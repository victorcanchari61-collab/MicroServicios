namespace reservations_ms.Domain.Entities;

public class Reservation
{
    public Guid Id { get; private set; }
    public Guid ClientId { get; private set; }
    public Guid HotelId { get; private set; }
    public string? HotelName { get; private set; }
    public string? RoomId { get; private set; }
    public string? RoomNumber { get; private set; }
    public DateTime CheckInDate { get; private set; }
    public DateTime CheckOutDate { get; private set; }
    public int NumberOfGuests { get; private set; }
    public decimal TotalPrice { get; private set; }
    public ReservationStatus Status { get; private set; }
    public DateTime CreatedAt { get; private set; }
    public DateTime UpdatedAt { get; private set; }

    private Reservation() { } // EF Core

    public Reservation(
        Guid clientId,
        Guid hotelId,
        DateTime checkInDate,
        DateTime checkOutDate,
        int numberOfGuests,
        decimal totalPrice,
        string? hotelName = null,
        string? roomId = null,
        string? roomNumber = null)
    {
        ValidateInputs(checkInDate, checkOutDate, numberOfGuests, totalPrice);

        Id = Guid.NewGuid();
        ClientId = clientId;
        HotelId = hotelId;
        HotelName = hotelName;
        RoomId = roomId;
        RoomNumber = roomNumber;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        NumberOfGuests = numberOfGuests;
        TotalPrice = totalPrice;
        Status = ReservationStatus.Pending;
        CreatedAt = DateTime.UtcNow;
        UpdatedAt = DateTime.UtcNow;
    }

    private void ValidateInputs(DateTime checkIn, DateTime checkOut, int guests, decimal price)
    {
        if (checkIn < DateTime.Today) throw new ArgumentException("Check-in date cannot be in the past");
        if (checkOut <= checkIn) throw new ArgumentException("Check-out must be after check-in");
        if (guests < 1) throw new ArgumentException("Number of guests must be at least 1");
        if (price <= 0) throw new ArgumentException("Total price must be greater than 0");
    }

    public void Confirm()
    {
        if (Status != ReservationStatus.Pending) throw new InvalidOperationException("Only pending reservations can be confirmed");
        Status = ReservationStatus.Confirmed;
        UpdatedAt = DateTime.UtcNow;
    }

    public void Cancel()
    {
        if (Status == ReservationStatus.Cancelled) throw new InvalidOperationException("Reservation already cancelled");
        if (Status == ReservationStatus.Completed) throw new InvalidOperationException("Cannot cancel completed reservation");
        Status = ReservationStatus.Cancelled;
        UpdatedAt = DateTime.UtcNow;
    }

    public void Complete()
    {
        if (Status != ReservationStatus.Confirmed) throw new InvalidOperationException("Only confirmed reservations can be completed");
        Status = ReservationStatus.Completed;
        UpdatedAt = DateTime.UtcNow;
    }
}

public enum ReservationStatus
{
    Pending,
    Confirmed,
    Cancelled,
    Completed
}
