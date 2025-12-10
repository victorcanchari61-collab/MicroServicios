using System.ComponentModel.DataAnnotations;

namespace reservations_ms.Application.DTOs;

public record CreateReservationRequest(
    [Required] Guid ClientId,
    [Required] Guid HotelId,
    [Required] DateTime CheckInDate,
    [Required] DateTime CheckOutDate,
    [Required][Range(1, 20)] int NumberOfGuests,
    [Required][Range(0.01, double.MaxValue)] decimal TotalPrice,
    [Required] string ClientName,
    [Required][EmailAddress] string ClientEmail,
    string? ClientPhone,
    [Required] string HotelName
);
