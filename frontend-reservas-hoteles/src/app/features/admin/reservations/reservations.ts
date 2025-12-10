import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ReservationService, Reservation } from '../../../core/services/reservation.service';

@Component({
  selector: 'app-admin-reservations',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservations.html',
  styleUrls: ['./reservations.scss']
})
export class AdminReservationsComponent implements OnInit {
  private authService = inject(AuthService);
  private reservationService = inject(ReservationService);
  private router = inject(Router);

  currentUser = this.authService.currentUser;
  reservations = signal<Reservation[]>([]);
  loading = signal(false);

  ngOnInit(): void {
    this.loadAllReservations();
  }

  loadAllReservations(): void {
    this.loading.set(true);
    this.reservationService.getAll().subscribe({
      next: (reservations: Reservation[]) => {
        this.reservations.set(reservations);
        this.loading.set(false);
      },
      error: (err: any) => {
        console.error('Error loading reservations:', err);
        this.loading.set(false);
        alert('Error al cargar las reservas');
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

  goToDashboard(): void {
    this.router.navigate(['/admin/dashboard']);
  }

  logout(): void {
    this.authService.logout();
  }
}
