using reservations_ms.Domain.Entities;

namespace reservations_ms.Domain.Repositories;

public interface IReservationRepository
{
    Task<Reservation> SaveAsync(Reservation reservation);
    Task<Reservation> UpdateAsync(Reservation reservation);
    Task<Reservation?> GetByIdAsync(Guid id);
    Task<IEnumerable<Reservation>> GetAllAsync();
    Task<IEnumerable<Reservation>> GetByClientIdAsync(Guid clientId);
    Task<IEnumerable<Reservation>> GetByHotelIdAsync(Guid hotelId);
    Task<bool> ExistsAsync(Guid id);
    Task DeleteAsync(Guid id);
}
