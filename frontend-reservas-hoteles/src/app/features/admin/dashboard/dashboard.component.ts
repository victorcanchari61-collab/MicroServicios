import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HotelService } from '../../../core/services/hotel.service';
import { Hotel } from '../../../core/models/hotel.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  private authService = inject(AuthService);
  private hotelService = inject(HotelService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  currentUser = this.authService.currentUser;
  hotels = signal<Hotel[]>([]);
  loading = signal(false);
  showModal = signal(false);
  editingHotel = signal<Hotel | null>(null);

  hotelForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    city: ['', Validators.required],
    country: ['', Validators.required],
    address: ['', Validators.required],
    stars: [3, [Validators.required, Validators.min(1), Validators.max(5)]],
    pricePerNight: [0, [Validators.required, Validators.min(0)]],
    totalRooms: [0, [Validators.required, Validators.min(0)]],
    availableRooms: [0, [Validators.required, Validators.min(0)]]
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

  openCreateModal(): void {
    this.editingHotel.set(null);
    this.hotelForm.reset({
      stars: 3,
      pricePerNight: 0,
      totalRooms: 0,
      availableRooms: 0
    });
    this.showModal.set(true);
  }

  openEditModal(hotel: Hotel): void {
    this.editingHotel.set(hotel);
    this.hotelForm.patchValue({
      name: hotel.name,
      description: hotel.description,
      city: hotel.city,
      country: hotel.country,
      address: hotel.address,
      stars: hotel.stars,
      pricePerNight: hotel.pricePerNight,
      totalRooms: hotel.totalRooms,
      availableRooms: hotel.availableRooms
    });
    this.showModal.set(true);
  }

  closeModal(): void {
    this.showModal.set(false);
    this.editingHotel.set(null);
    this.hotelForm.reset();
  }

  submitHotel(): void {
    if (this.hotelForm.invalid) return;

    const hotelData = this.hotelForm.value as any;
    this.loading.set(true);

    const editing = this.editingHotel();
    const request = editing 
      ? this.hotelService.update(editing.id, hotelData)
      : this.hotelService.create(hotelData);

    request.subscribe({
      next: () => {
        this.loading.set(false);
        this.closeModal();
        this.loadHotels();
      },
      error: (err) => {
        console.error('Error saving hotel:', err);
        this.loading.set(false);
        
        if (err.status === 401) {
          alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
          this.logout();
        } else {
          alert('Error al guardar el hotel. Intenta nuevamente.');
        }
      }
    });
  }

  deleteHotel(hotel: Hotel): void {
    if (!confirm(`¿Estás seguro de eliminar ${hotel.name}?`)) return;

    this.loading.set(true);
    this.hotelService.delete(hotel.id).subscribe({
      next: () => {
        this.loading.set(false);
        this.loadHotels();
      },
      error: (err) => {
        console.error('Error deleting hotel:', err);
        this.loading.set(false);
        
        if (err.status === 401) {
          alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
          this.logout();
        } else {
          alert('Error al eliminar el hotel.');
        }
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }

  manageRooms(hotelId: string): void {
    this.router.navigate(['/admin/rooms', hotelId]);
  }
}
