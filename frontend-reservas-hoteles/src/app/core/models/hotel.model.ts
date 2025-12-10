export interface Hotel {
  id: string;
  name: string;
  description: string;
  address: string;
  city: string;
  country: string;
  stars: number;
  pricePerNight: number;
  totalRooms: number;
  availableRooms: number;
  createdAt: string;
  updatedAt: string;
}

export interface CreateHotelRequest {
  name: string;
  description: string;
  address: string;
  city: string;
  country: string;
  stars: number;
  pricePerNight: number;
  totalRooms: number;
  availableRooms: number;
}
