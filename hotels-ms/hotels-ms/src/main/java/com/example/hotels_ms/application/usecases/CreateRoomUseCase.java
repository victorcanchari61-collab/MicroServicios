package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.CreateRoomRequest;
import com.example.hotels_ms.application.dto.RoomResponse;
import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;

@Service
public class CreateRoomUseCase {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public CreateRoomUseCase(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public RoomResponse execute(String hotelIdStr, CreateRoomRequest request) {
        HotelId hotelId = HotelId.from(hotelIdStr);
        
        // Verify hotel exists
        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        
        // Check if room number already exists for this hotel
        if (roomRepository.existsByHotelIdAndRoomNumber(hotelId, request.getRoomNumber())) {
            throw new RuntimeException("Room number already exists for this hotel");
        }
        
        Room room = new Room(
                RoomId.generate(),
                hotelId,
                request.getRoomNumber(),
                request.getRoomType(),
                request.getCapacity(),
                request.getPricePerNight(),
                request.getDescription(),
                true
        );
        
        Room savedRoom = roomRepository.save(room);
        return toResponse(savedRoom);
    }

    private RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId().getValue(),
                room.getHotelId().getValue().toString(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getDescription(),
                room.getIsAvailable()
        );
    }
}
