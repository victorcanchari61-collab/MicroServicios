using reservations_ms.Application.DTOs;
using reservations_ms.Application.Services;
using reservations_ms.Domain.Entities;
using reservations_ms.Domain.Repositories;
using reservations_ms.Infrastructure.Services;

namespace reservations_ms.Application.UseCases;

public class CreateReservationUseCase
{
    private readonly IReservationRepository _repository;
    private readonly INotificationService _notificationService;
    private readonly HotelsHttpClient _hotelsClient;
    private readonly ILogger<CreateReservationUseCase> _logger;

    public CreateReservationUseCase(
        IReservationRepository repository, 
        INotificationService notificationService,
        HotelsHttpClient hotelsClient,
        ILogger<CreateReservationUseCase> logger)
    {
        _repository = repository;
        _notificationService = notificationService;
        _hotelsClient = hotelsClient;
        _logger = logger;
    }

    public async Task<ReservationResponse> ExecuteAsync(CreateReservationRequest request)
    {
        // Try to get an available room
        string? roomId = null;
        string? roomNumber = null;
        
        var availableRoom = await _hotelsClient.GetAvailableRoomAsync(request.HotelId);
        if (availableRoom != null)
        {
            // Hold the room
            var held = await _hotelsClient.HoldRoomAsync(availableRoom.Id);
            if (held)
            {
                roomId = availableRoom.Id;
                roomNumber = availableRoom.RoomNumber;
                _logger.LogInformation($"Room {roomNumber} assigned to reservation");
            }
            else
            {
                _logger.LogWarning($"Failed to hold room {availableRoom.Id}");
            }
        }
        else
        {
            _logger.LogWarning($"No available rooms found for hotel {request.HotelId}");
        }

        var reservation = new Reservation(
            request.ClientId,
            request.HotelId,
            request.CheckInDate,
            request.CheckOutDate,
            request.NumberOfGuests,
            request.TotalPrice,
            request.HotelName,
            roomId,
            roomNumber
        );

        var saved = await _repository.SaveAsync(reservation);
        
        // Send email notification (async, no wait)
        _ = _notificationService.SendReservationConfirmationAsync(
            request.ClientEmail,
            request.ClientName,
            request.HotelName,
            request.CheckInDate,
            request.CheckOutDate,
            request.TotalPrice,
            saved.Id.ToString()
        );
        
        // Generate WhatsApp link
        var whatsappLink = string.IsNullOrEmpty(request.ClientPhone) 
            ? null 
            : _notificationService.GenerateWhatsAppLink(
                request.ClientPhone,
                request.ClientName,
                request.HotelName,
                request.CheckInDate,
                request.CheckOutDate,
                request.TotalPrice,
                saved.Id.ToString()
            );
        
        var response = ReservationResponse.FromEntity(saved);
        response.WhatsAppLink = whatsappLink;
        
        return response;
    }
}
