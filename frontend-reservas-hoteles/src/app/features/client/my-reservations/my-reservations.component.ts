import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ReservationService, Reservation } from '../../../core/services/reservation.service';
import { HotelService } from '../../../core/services/hotel.service';

@Component({
  selector: 'app-my-reservations',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-reservations.component.html',
  styleUrl: './my-reservations.component.scss'
})
export class MyReservationsComponent implements OnInit {
  private authService = inject(AuthService);
  private reservationService = inject(ReservationService);
  private hotelService = inject(HotelService);
  private router = inject(Router);

  currentUser = this.authService.currentUser;
  reservations = signal<Reservation[]>([]);
  loading = signal(false);

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    const user = this.currentUser();
    if (!user) return;

    this.loading.set(true);
    this.reservationService.getByClientId(user.id).subscribe({
      next: (reservations) => {
        this.reservations.set(reservations);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading reservations:', err);
        this.loading.set(false);
      }
    });
  }

  cancelReservation(reservation: Reservation): void {
    if (!confirm('¿Estás seguro de cancelar esta reserva?')) return;

    this.loading.set(true);
    this.reservationService.cancel(reservation.id).subscribe({
      next: () => {
        this.loading.set(false);
        this.loadReservations();
        alert('Reserva cancelada exitosamente');
      },
      error: (err) => {
        console.error('Error cancelling reservation:', err);
        this.loading.set(false);
        alert('Error al cancelar la reserva');
      }
    });
  }

  getStatusClass(status: string): string {
    return status.toLowerCase();
  }

  getStatusText(status: string): string {
    const statusMap: any = {
      'Pending': 'Pendiente',
      'Confirmed': 'Confirmada',
      'Cancelled': 'Cancelada',
      'Completed': 'Completada'
    };
    return statusMap[status] || status;
  }

  goToHotels(): void {
    this.router.navigate(['/client/hotels']);
  }

  logout(): void {
    this.authService.logout();
  }
}
