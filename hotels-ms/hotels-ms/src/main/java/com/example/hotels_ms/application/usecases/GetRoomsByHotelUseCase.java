package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.RoomResponse;
import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetRoomsByHotelUseCase {
    private final RoomRepository roomRepository;

    public GetRoomsByHotelUseCase(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> execute(String hotelIdStr) {
        HotelId hotelId = HotelId.from(hotelIdStr);
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        
        return rooms.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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
