import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Reservation {
  id: string;
  clientId: string;
  hotelId: string;
  hotelName?: string;
  roomNumber?: string;
  checkInDate: string;
  checkOutDate: string;
  numberOfGuests: number;
  totalPrice: number;
  status: string;
  createdAt: string;
  updatedAt: string;
  whatsAppLink?: string;
}

export interface CreateReservationRequest {
  clientId: string;
  hotelId: string;
  checkInDate: string;
  checkOutDate: string;
  numberOfGuests: number;
  totalPrice: number;
  clientName: string;
  clientEmail: string;
  clientPhone?: string;
  hotelName: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/reservations`;

  create(reservation: CreateReservationRequest): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  getByClientId(clientId: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/client/${clientId}`);
  }

  cancel(id: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/cancel`, {});
  }

  getAll(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl);
  }
}
