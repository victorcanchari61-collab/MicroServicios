using reservations_ms.Domain.Repositories;

namespace reservations_ms.Application.UseCases;

public class CancelReservationUseCase
{
    private readonly IReservationRepository _repository;

    public CancelReservationUseCase(IReservationRepository repository)
    {
        _repository = repository;
    }

    public async Task<bool> ExecuteAsync(Guid reservationId)
    {
        var reservation = await _repository.GetByIdAsync(reservationId);
        
        if (reservation == null)
        {
            throw new KeyNotFoundException("Reservation not found");
        }

        reservation.Cancel();
        await _repository.UpdateAsync(reservation);
        
        return true;
    }
}
