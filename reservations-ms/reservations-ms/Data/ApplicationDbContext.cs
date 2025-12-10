using Microsoft.EntityFrameworkCore;
using reservations_ms.Domain.Entities;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options)
    {
    }

    public DbSet<Reservation> Reservations { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Reservation>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.Property(e => e.ClientId).IsRequired();
            entity.Property(e => e.HotelId).IsRequired();
            entity.Property(e => e.CheckInDate).IsRequired();
            entity.Property(e => e.CheckOutDate).IsRequired();
            entity.Property(e => e.NumberOfGuests).IsRequired();
            entity.Property(e => e.TotalPrice).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(e => e.Status).HasConversion<string>().IsRequired();
            entity.Property(e => e.CreatedAt).IsRequired();
            entity.Property(e => e.UpdatedAt).IsRequired();
        });
    }
}
