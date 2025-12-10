package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.RoomResponse;
import com.example.hotels_ms.application.dto.UpdateRoomRequest;
import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;

@Service
public class UpdateRoomUseCase {
    private final RoomRepository roomRepository;

    public UpdateRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomResponse execute(String roomIdStr, UpdateRoomRequest request) {
        RoomId roomId = new RoomId(roomIdStr);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        room.updateInfo(
                request.getRoomNumber(),
                request.getRoomType(),
                request.getCapacity(),
                request.getDescription()
        );
        
        room.updatePrice(request.getPricePerNight());
        
        if (request.getIsAvailable() != null) {
            room.setAvailability(request.getIsAvailable());
        }
        
        Room updatedRoom = roomRepository.save(room);
        return toResponse(updatedRoom);
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
