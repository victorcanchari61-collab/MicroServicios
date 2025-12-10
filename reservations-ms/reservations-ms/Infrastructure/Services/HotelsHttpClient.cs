using System.Text.Json;

namespace reservations_ms.Infrastructure.Services;

public class HotelsHttpClient
{
    private readonly HttpClient _httpClient;
    private readonly ILogger<HotelsHttpClient> _logger;

    public HotelsHttpClient(HttpClient httpClient, ILogger<HotelsHttpClient> logger)
    {
        _httpClient = httpClient;
        _logger = logger;
    }

    public async Task<RoomDto?> GetAvailableRoomAsync(Guid hotelId)
    {
        try
        {
            var response = await _httpClient.GetAsync($"/api/hotels/{hotelId}/rooms");
            
            if (!response.IsSuccessStatusCode)
            {
                _logger.LogWarning($"Failed to get rooms for hotel {hotelId}: {response.StatusCode}");
                return null;
            }

            var content = await response.Content.ReadAsStringAsync();
            var rooms = JsonSerializer.Deserialize<List<RoomDto>>(content, new JsonSerializerOptions 
            { 
                PropertyNameCaseInsensitive = true 
            });

            // Return first available room
            return rooms?.FirstOrDefault(r => r.IsAvailable);
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, $"Error getting available room for hotel {hotelId}");
            return null;
        }
    }

    public async Task<bool> HoldRoomAsync(string roomId)
    {
        try
        {
            var response = await _httpClient.PostAsync($"/api/rooms/{roomId}/hold", null);
            return response.IsSuccessStatusCode;
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, $"Error holding room {roomId}");
            return false;
        }
    }
}

public class RoomDto
{
    public string Id { get; set; } = string.Empty;
    public string HotelId { get; set; } = string.Empty;
    public string RoomNumber { get; set; } = string.Empty;
    public string RoomType { get; set; } = string.Empty;
    public int Capacity { get; set; }
    public decimal PricePerNight { get; set; }
    public string? Description { get; set; }
    public bool IsAvailable { get; set; }
}
