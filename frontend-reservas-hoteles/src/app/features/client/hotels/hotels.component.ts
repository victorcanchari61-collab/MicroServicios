import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HotelService } from '../../../core/services/hotel.service';
import { ReservationService } from '../../../core/services/reservation.service';
import { Hotel } from '../../../core/models/hotel.model';

@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './hotels.component.html',
  styleUrl: './hotels.component.scss'
})
export class HotelsComponent implements OnInit {
  private authService = inject(AuthService);
  private hotelService = inject(HotelService);
  private reservationService = inject(ReservationService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  
  currentUser = this.authService.currentUser;
  hotels = signal<Hotel[]>([]);
  loading = signal(false);
  selectedHotel = signal<Hotel | null>(null);
  showReservationModal = signal(false);
  reservationSuccess = signal(false);
  whatsappLink = signal<string | null>(null);
  minDate = new Date().toISOString().split('T')[0];

  reservationForm = this.fb.group({
    checkInDate: ['', Validators.required],
    checkOutDate: ['', Validators.required],
    numberOfGuests: [1, [Validators.required, Validators.min(1)]],
    phone: ['']
  });

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.loading.set(true);
    this.hotelService.getAll().subscribe({
      next: (hotels) => {
        this.hotels.set(hotels);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading hotels:', err);
        this.loading.set(false);
      }
    });
  }

  openReservationModal(hotel: Hotel): void {
    this.selectedHotel.set(hotel);
    this.showReservationModal.set(true);
    this.reservationSuccess.set(false);
    this.reservationForm.reset({ numberOfGuests: 1 });
  }

  closeModal(): void {
    this.showReservationModal.set(false);
    this.selectedHotel.set(null);
    this.reservationSuccess.set(false);
    this.whatsappLink.set(null);
  }

  calculateTotal(): number {
    const hotel = this.selectedHotel();
    const checkIn = this.reservationForm.get('checkInDate')?.value;
    const checkOut = this.reservationForm.get('checkOutDate')?.value;
    
    if (!hotel || !checkIn || !checkOut) return 0;
    
    const days = Math.ceil((new Date(checkOut).getTime() - new Date(checkIn).getTime()) / (1000 * 60 * 60 * 24));
    return days > 0 ? days * hotel.pricePerNight : 0;
  }

  submitReservation(): void {
    if (this.reservationForm.invalid || !this.selectedHotel() || !this.currentUser()) return;

    const hotel = this.selectedHotel()!;
    const user = this.currentUser()!;
    const formValue = this.reservationForm.value;

    const reservation = {
      clientId: user.id,
      hotelId: hotel.id,
      checkInDate: formValue.checkInDate!,
      checkOutDate: formValue.checkOutDate!,
      numberOfGuests: formValue.numberOfGuests!,
      totalPrice: this.calculateTotal(),
      clientName: user.name,
      clientEmail: user.email,
      clientPhone: formValue.phone || undefined,
      hotelName: hotel.name
    };

    this.loading.set(true);
    this.reservationService.create(reservation).subscribe({
      next: (response) => {
        this.loading.set(false);
        this.reservationSuccess.set(true);
        this.whatsappLink.set(response.whatsAppLink || null);
      },
      error: (err) => {
        console.error('Error creating reservation:', err);
        this.loading.set(false);
        alert('Error al crear la reserva. Intenta nuevamente.');
      }
    });
  }

  openWhatsApp(): void {
    const link = this.whatsappLink();
    if (link) {
      window.open(link, '_blank');
    }
  }

  logout(): void {
    this.authService.logout();
  }

  goToMyReservations(): void {
    this.router.navigate(['/client/my-reservations']);
  }
}
