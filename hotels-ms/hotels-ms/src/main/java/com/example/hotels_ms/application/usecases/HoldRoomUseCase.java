package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;

@Service
public class HoldRoomUseCase {
    private final RoomRepository roomRepository;

    public HoldRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void execute(String roomIdStr) {
        RoomId roomId = new RoomId(roomIdStr);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        if (!room.getIsAvailable()) {
            throw new RuntimeException("Room is already held or unavailable");
        }
        
        room.hold();
        roomRepository.save(room);
    }
}
