package com.example.hotels_ms.domain.repositories;

import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findById(RoomId id);
    List<Room> findByHotelId(HotelId hotelId);
    List<Room> findAvailableByHotelId(HotelId hotelId);
    void deleteById(RoomId id);
    boolean existsByHotelIdAndRoomNumber(HotelId hotelId, String roomNumber);
}
