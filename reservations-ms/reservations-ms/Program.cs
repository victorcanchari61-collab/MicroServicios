using Microsoft.EntityFrameworkCore;
using reservations_ms.Application.Services;
using reservations_ms.Application.UseCases;
using reservations_ms.Domain.Repositories;
using reservations_ms.Infrastructure.Persistence;
using reservations_ms.Infrastructure.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

// Register HttpClient for NotificationService
builder.Services.AddHttpClient<INotificationService, NotificationService>();

// Register HttpClient for HotelsService
builder.Services.AddHttpClient<HotelsHttpClient>(client =>
{
    client.BaseAddress = new Uri("http://hotels-service:8080");
    client.Timeout = TimeSpan.FromSeconds(30);
});

// Register repositories (DIP: Depend on abstractions)
builder.Services.AddScoped<IReservationRepository, ReservationRepository>();

// Register services
builder.Services.AddScoped<INotificationService, NotificationService>();

// Register use cases
builder.Services.AddScoped<CreateReservationUseCase>();
builder.Services.AddScoped<GetReservationByIdUseCase>();
builder.Services.AddScoped<GetReservationsByClientUseCase>();
builder.Services.AddScoped<GetAllReservationsUseCase>();
builder.Services.AddScoped<CancelReservationUseCase>();

// Add CORS
builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(policy =>
    {
        policy.AllowAnyOrigin()
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Auto-migrate database
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
    db.Database.EnsureCreated(); // Creates database and tables if they don't exist
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
