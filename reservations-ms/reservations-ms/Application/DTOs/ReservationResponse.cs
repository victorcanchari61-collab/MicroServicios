using reservations_ms.Domain.Entities;

namespace reservations_ms.Application.DTOs;

public class ReservationResponse
{
    public Guid Id { get; set; }
    public Guid ClientId { get; set; }
    public Guid HotelId { get; set; }
    public string? HotelName { get; set; }
    public string? RoomNumber { get; set; }
    public DateTime CheckInDate { get; set; }
    public DateTime CheckOutDate { get; set; }
    public int NumberOfGuests { get; set; }
    public decimal TotalPrice { get; set; }
    public string Status { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public string? WhatsAppLink { get; set; }

    public static ReservationResponse FromEntity(Reservation reservation) => new()
    {
        Id = reservation.Id,
        ClientId = reservation.ClientId,
        HotelId = reservation.HotelId,
        HotelName = reservation.HotelName,
        RoomNumber = reservation.RoomNumber,
        CheckInDate = reservation.CheckInDate,
        CheckOutDate = reservation.CheckOutDate,
        NumberOfGuests = reservation.NumberOfGuests,
        TotalPrice = reservation.TotalPrice,
        Status = reservation.Status.ToString(),
        CreatedAt = reservation.CreatedAt,
        UpdatedAt = reservation.UpdatedAt
    };
}
