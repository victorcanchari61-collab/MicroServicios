namespace reservations_ms.Application.Services;

public interface INotificationService
{
    Task SendReservationConfirmationAsync(string email, string clientName, string hotelName, 
        DateTime checkIn, DateTime checkOut, decimal totalPrice, string reservationId);
    
    string GenerateWhatsAppLink(string phone, string clientName, string hotelName, 
        DateTime checkIn, DateTime checkOut, decimal totalPrice, string reservationId);
}
