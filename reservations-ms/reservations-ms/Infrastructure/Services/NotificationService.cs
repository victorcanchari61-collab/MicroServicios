using System.Text;
using System.Web;
using reservations_ms.Application.Services;

namespace reservations_ms.Infrastructure.Services;

public class NotificationService : INotificationService
{
    private readonly HttpClient _httpClient;
    private readonly IConfiguration _configuration;
    private readonly ILogger<NotificationService> _logger;

    public NotificationService(HttpClient httpClient, IConfiguration configuration, ILogger<NotificationService> logger)
    {
        _httpClient = httpClient;
        _configuration = configuration;
        _logger = logger;
    }

    public async Task SendReservationConfirmationAsync(string email, string clientName, string hotelName,
        DateTime checkIn, DateTime checkOut, decimal totalPrice, string reservationId)
    {
        try
        {
            var notificationUrl = _configuration["NotificationsService:Url"] ?? "http://notifications-service:8080";
            
            var request = new
            {
                email = email,
                phone = (string?)null,
                subject = "Confirmación de Reserva - Hotel",
                eventType = "ReservationCreated",
                variables = new Dictionary<string, string>
                {
                    { "clientName", clientName },
                    { "hotelName", hotelName },
                    { "checkInDate", checkIn.ToString("dd/MM/yyyy") },
                    { "checkOutDate", checkOut.ToString("dd/MM/yyyy") },
                    { "totalPrice", $"S/ {totalPrice:F2}" },
                    { "reservationId", reservationId }
                },
                reservationId = reservationId
            };

            var response = await _httpClient.PostAsJsonAsync($"{notificationUrl}/api/notifications/send", request);
            
            if (response.IsSuccessStatusCode)
            {
                _logger.LogInformation("Email sent successfully to {Email}", email);
            }
            else
            {
                var errorContent = await response.Content.ReadAsStringAsync();
                _logger.LogWarning("Failed to send email to {Email}. Status: {Status}, Error: {Error}", 
                    email, response.StatusCode, errorContent);
            }
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error sending email notification to {Email}", email);
        }
    }

    public string GenerateWhatsAppLink(string phone, string clientName, string hotelName,
        DateTime checkIn, DateTime checkOut, decimal totalPrice, string reservationId)
    {
        // Remove any non-numeric characters from phone
        var cleanPhone = new string(phone.Where(char.IsDigit).ToArray());
        
        // Build message
        var message = new StringBuilder();
        message.AppendLine($"🎉 *Confirmación de Reserva*");
        message.AppendLine();
        message.AppendLine($"Hola *{clientName}*,");
        message.AppendLine();
        message.AppendLine($"Tu reserva ha sido confirmada:");
        message.AppendLine($"🏨 Hotel: *{hotelName}*");
        message.AppendLine($"📅 Check-in: {checkIn:dd/MM/yyyy}");
        message.AppendLine($"📅 Check-out: {checkOut:dd/MM/yyyy}");
        message.AppendLine($"💰 Total: S/ {totalPrice:F2}");
        message.AppendLine();
        message.AppendLine($"Código de reserva: *{reservationId}*");
        message.AppendLine();
        message.AppendLine("¡Gracias por tu preferencia!");

        // URL encode the message
        var encodedMessage = HttpUtility.UrlEncode(message.ToString());
        
        // Generate WhatsApp link
        return $"https://wa.me/{cleanPhone}?text={encodedMessage}";
    }
}
