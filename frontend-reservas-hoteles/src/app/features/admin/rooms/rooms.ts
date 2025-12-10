import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RoomService, Room, CreateRoomRequest, UpdateRoomRequest } from '../../../core/services/room.service';

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rooms.html',
  styleUrls: ['./rooms.scss']
})
export class RoomsComponent implements OnInit {
  hotelId: string = '';
  rooms = signal<Room[]>([]);
  loading = signal(false);
  showModal = signal(false);
  isEditMode = false;
  currentRoom: UpdateRoomRequest = this.getEmptyRoom();
  currentRoomId: string = '';

  roomTypes = ['SINGLE', 'DOUBLE', 'SUITE', 'DELUXE'];

  constructor(
    private roomService: RoomService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.hotelId = this.route.snapshot.paramMap.get('hotelId') || '';
    if (this.hotelId) {
      this.loadRooms();
    }
  }

  loadRooms() {
    this.loading.set(true);
    this.roomService.getRoomsByHotel(this.hotelId).subscribe({
      next: (rooms) => {
        this.rooms.set(rooms);
        this.loading.set(false);
      },
      error: (error) => {
        console.error('Error loading rooms:', error);
        this.loading.set(false);
        alert('Error al cargar las habitaciones');
      }
    });
  }

  openCreateModal() {
    this.isEditMode = false;
    this.currentRoom = this.getEmptyRoom();
    this.showModal.set(true);
  }

  openEditModal(room: Room) {
    this.isEditMode = true;
    this.currentRoomId = room.id;
    this.currentRoom = {
      roomNumber: room.roomNumber,
      roomType: room.roomType,
      capacity: room.capacity,
      pricePerNight: room.pricePerNight,
      description: room.description,
      isAvailable: room.isAvailable
    };
    this.showModal.set(true);
  }

  closeModal() {
    this.showModal.set(false);
    this.currentRoom = this.getEmptyRoom();
    this.currentRoomId = '';
  }

  saveRoom() {
    if (this.isEditMode) {
      this.roomService.updateRoom(this.currentRoomId, this.currentRoom).subscribe({
        next: () => {
          this.loadRooms();
          this.closeModal();
          alert('Habitación actualizada exitosamente');
        },
        error: (error) => {
          console.error('Error updating room:', error);
          alert('Error al actualizar la habitación');
        }
      });
    } else {
      const createRequest: CreateRoomRequest = {
        roomNumber: this.currentRoom.roomNumber,
        roomType: this.currentRoom.roomType,
        capacity: this.currentRoom.capacity,
        pricePerNight: this.currentRoom.pricePerNight,
        description: this.currentRoom.description
      };
      
      this.roomService.createRoom(this.hotelId, createRequest).subscribe({
        next: () => {
          this.loadRooms();
          this.closeModal();
          alert('Habitación creada exitosamente');
        },
        error: (error) => {
          console.error('Error creating room:', error);
          alert('Error al crear la habitación');
        }
      });
    }
  }

  deleteRoom(roomId: string) {
    if (confirm('¿Estás seguro de eliminar esta habitación?')) {
      this.roomService.deleteRoom(roomId).subscribe({
        next: () => {
          alert('Habitación eliminada exitosamente');
          this.loadRooms();
        },
        error: (error) => {
          console.error('Error deleting room:', error);
          alert('Error al eliminar la habitación');
        }
      });
    }
  }

  toggleAvailability(room: Room) {
    const updateRequest: UpdateRoomRequest = {
      roomNumber: room.roomNumber,
      roomType: room.roomType,
      capacity: room.capacity,
      pricePerNight: room.pricePerNight,
      description: room.description,
      isAvailable: !room.isAvailable
    };

    this.roomService.updateRoom(room.id, updateRequest).subscribe({
      next: () => {
        this.loadRooms();
      },
      error: (error) => {
        console.error('Error toggling availability:', error);
        alert('Error al cambiar disponibilidad');
      }
    });
  }

  goBack() {
    this.router.navigate(['/admin/dashboard']);
  }

  private getEmptyRoom(): UpdateRoomRequest {
    return {
      roomNumber: '',
      roomType: 'SINGLE',
      capacity: 1,
      pricePerNight: 0,
      description: '',
      isAvailable: true
    };
  }
}
