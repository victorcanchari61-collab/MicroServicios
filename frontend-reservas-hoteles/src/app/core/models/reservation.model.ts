export interface Reservation {
  id: string;
  clientId: string;
  hotelId: string;
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
