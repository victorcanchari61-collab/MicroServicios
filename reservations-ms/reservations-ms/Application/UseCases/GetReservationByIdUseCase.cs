using reservations_ms.Application.DTOs;
using reservations_ms.Domain.Repositories;

namespace reservations_ms.Application.UseCases;

public class GetReservationByIdUseCase
{
    private readonly IReservationRepository _repository;

    public GetReservationByIdUseCase(IReservationRepository repository)
    {
        _repository = repository;
    }

    public async Task<ReservationResponse> ExecuteAsync(Guid id)
    {
        var reservation = await _repository.GetByIdAsync(id)
            ?? throw new KeyNotFoundException($"Reservation not found with id: {id}");

        return ReservationResponse.FromEntity(reservation);
    }
}
