using Microsoft.EntityFrameworkCore;
using reservations_ms.Domain.Entities;
using reservations_ms.Domain.Repositories;

namespace reservations_ms.Infrastructure.Persistence;

public class ReservationRepository : IReservationRepository
{
    private readonly ApplicationDbContext _context;

    public ReservationRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public async Task<Reservation> SaveAsync(Reservation reservation)
    {
        await _context.Reservations.AddAsync(reservation);
        await _context.SaveChangesAsync();
        return reservation;
    }

    public async Task<Reservation> UpdateAsync(Reservation reservation)
    {
        _context.Reservations.Update(reservation);
        await _context.SaveChangesAsync();
        return reservation;
    }

    public async Task<Reservation?> GetByIdAsync(Guid id)
    {
        return await _context.Reservations.FindAsync(id);
    }

    public async Task<IEnumerable<Reservation>> GetAllAsync()
    {
        return await _context.Reservations.ToListAsync();
    }

    public async Task<IEnumerable<Reservation>> GetByClientIdAsync(Guid clientId)
    {
        return await _context.Reservations
            .Where(r => r.ClientId == clientId)
            .ToListAsync();
    }

    public async Task<IEnumerable<Reservation>> GetByHotelIdAsync(Guid hotelId)
    {
        return await _context.Reservations
            .Where(r => r.HotelId == hotelId)
            .ToListAsync();
    }

    public async Task<bool> ExistsAsync(Guid id)
    {
        return await _context.Reservations.AnyAsync(r => r.Id == id);
    }

    public async Task DeleteAsync(Guid id)
    {
        var reservation = await GetByIdAsync(id);
        if (reservation != null)
        {
            _context.Reservations.Remove(reservation);
            await _context.SaveChangesAsync();
        }
    }
}
