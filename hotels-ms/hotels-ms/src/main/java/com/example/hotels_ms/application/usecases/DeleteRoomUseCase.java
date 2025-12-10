package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoomUseCase {
    private final RoomRepository roomRepository;

    public DeleteRoomUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void execute(String roomIdStr) {
        RoomId roomId = new RoomId(roomIdStr);
        
        if (!roomRepository.findById(roomId).isPresent()) {
            throw new RuntimeException("Room not found");
        }
        
        roomRepository.deleteById(roomId);
    }
}
