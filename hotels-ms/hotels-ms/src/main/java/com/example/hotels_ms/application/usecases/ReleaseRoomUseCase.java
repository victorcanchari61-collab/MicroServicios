package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReleaseRoomUseCase {
    private final RoomRepository roomRepository;

    public ReleaseRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void execute(String roomId) {
        RoomId id = new RoomId(roomId);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        room.release();
        roomRepository.save(room);
    }
}
