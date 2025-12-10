using Microsoft.AspNetCore.Mvc;
using reservations_ms.Application.DTOs;
using reservations_ms.Application.UseCases;

namespace reservations_ms.Presentation.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ReservationsController : ControllerBase
{
    private readonly CreateReservationUseCase _createUseCase;
    private readonly GetReservationByIdUseCase _getByIdUseCase;

    public ReservationsController(
        CreateReservationUseCase createUseCase,
        GetReservationByIdUseCase getByIdUseCase)
    {
        _createUseCase = createUseCase;
        _getByIdUseCase = getByIdUseCase;
    }

    [HttpPost]
    public async Task<ActionResult<ReservationResponse>> Create([FromBody] CreateReservationRequest request)
    {
        try
        {
            var response = await _createUseCase.ExecuteAsync(request);
            return CreatedAtAction(nameof(GetById), new { id = response.Id }, response);
        }
        catch (ArgumentException ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<ReservationResponse>> GetById(Guid id)
    {
        try
        {
            var response = await _getByIdUseCase.ExecuteAsync(id);
            return Ok(response);
        }
        catch (KeyNotFoundException ex)
        {
            return NotFound(new { error = ex.Message });
        }
    }

    [HttpGet]
    public async Task<ActionResult<List<ReservationResponse>>> GetAll()
    {
        var getAllUseCase = HttpContext.RequestServices.GetRequiredService<GetAllReservationsUseCase>();
        var reservations = await getAllUseCase.ExecuteAsync();
        return Ok(reservations);
    }

    [HttpGet("client/{clientId}")]
    public async Task<ActionResult<List<ReservationResponse>>> GetByClientId(string clientId)
    {
        if (!Guid.TryParse(clientId, out var clientGuid))
        {
            return BadRequest(new { error = "Invalid client ID format" });
        }

        var getByClientUseCase = HttpContext.RequestServices.GetRequiredService<GetReservationsByClientUseCase>();
        var reservations = await getByClientUseCase.ExecuteAsync(clientGuid);
        return Ok(reservations);
    }

    [HttpPut("{id}/cancel")]
    public async Task<ActionResult> CancelReservation(Guid id)
    {
        try
        {
            var cancelUseCase = HttpContext.RequestServices.GetRequiredService<CancelReservationUseCase>();
            await cancelUseCase.ExecuteAsync(id);
            return Ok(new { message = "Reservation cancelled successfully" });
        }
        catch (KeyNotFoundException ex)
        {
            return NotFound(new { error = ex.Message });
        }
        catch (InvalidOperationException ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }
}
