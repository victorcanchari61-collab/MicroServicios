using reservations_ms.Application.DTOs;
using reservations_ms.Domain.Repositories;

namespace reservations_ms.Application.UseCases;

public class GetAllReservationsUseCase
{
    private readonly IReservationRepository _repository;

    public GetAllReservationsUseCase(IReservationRepository repository)
    {
        _repository = repository;
    }

    public async Task<List<ReservationResponse>> ExecuteAsync()
    {
        var reservations = await _repository.GetAllAsync();
        return reservations.Select(ReservationResponse.FromEntity).ToList();
    }
}
