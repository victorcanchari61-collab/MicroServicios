using reservations_ms.Application.DTOs;
using reservations_ms.Domain.Repositories;

namespace reservations_ms.Application.UseCases;

public class GetReservationsByClientUseCase
{
    private readonly IReservationRepository _repository;

    public GetReservationsByClientUseCase(IReservationRepository repository)
    {
        _repository = repository;
    }

    public async Task<List<ReservationResponse>> ExecuteAsync(Guid clientId)
    {
        var reservations = await _repository.GetByClientIdAsync(clientId);
        return reservations.Select(ReservationResponse.FromEntity).ToList();
    }
}
