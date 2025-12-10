package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.RoomResponse;
import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;

@Service
public class GetRoomByIdUseCase {
    private final RoomRepository roomRepository;

    public GetRoomByIdUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomResponse execute(String roomIdStr) {
        RoomId roomId = new RoomId(roomIdStr);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        return toResponse(room);
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
