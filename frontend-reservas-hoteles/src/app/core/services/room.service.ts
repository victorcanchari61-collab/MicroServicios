import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Room {
  id: string;
  hotelId: string;
  roomNumber: string;
  roomType: string;
  capacity: number;
  pricePerNight: number;
  description: string;
  isAvailable: boolean;
}

export interface CreateRoomRequest {
  roomNumber: string;
  roomType: string;
  capacity: number;
  pricePerNight: number;
  description?: string;
}

export interface UpdateRoomRequest {
  roomNumber: string;
  roomType: string;
  capacity: number;
  pricePerNight: number;
  description?: string;
  isAvailable?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getRoomsByHotel(hotelId: string): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.apiUrl}/hotels/${hotelId}/rooms`);
  }

  getRoomById(roomId: string): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/rooms/${roomId}`);
  }

  createRoom(hotelId: string, room: CreateRoomRequest): Observable<Room> {
    return this.http.post<Room>(`${this.apiUrl}/hotels/${hotelId}/rooms`, room);
  }

  updateRoom(roomId: string, room: UpdateRoomRequest): Observable<Room> {
    return this.http.put<Room>(`${this.apiUrl}/rooms/${roomId}`, room);
  }

  deleteRoom(roomId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/rooms/${roomId}`);
  }

  holdRoom(roomId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/rooms/${roomId}/hold`, {});
  }

  releaseRoom(roomId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/rooms/${roomId}/release`, {});
  }
}
