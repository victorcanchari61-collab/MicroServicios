package com.example.hotels_ms.infrastructure.persistence;

import com.example.hotels_ms.domain.entities.Room;
import com.example.hotels_ms.domain.repositories.RoomRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoomRepositoryImpl implements RoomRepository {
    private final JpaRoomRepository jpaRoomRepository;

    public RoomRepositoryImpl(JpaRoomRepository jpaRoomRepository) {
        this.jpaRoomRepository = jpaRoomRepository;
    }

    @Override
    public Room save(Room room) {
        JpaRoomEntity entity = toEntity(room);
        JpaRoomEntity saved = jpaRoomRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Room> findById(RoomId id) {
        return jpaRoomRepository.findById(id.getValue()).map(this::toDomain);
    }

    @Override
    public List<Room> findByHotelId(HotelId hotelId) {
        return jpaRoomRepository.findByHotelId(hotelId.getValue().toString())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findAvailableByHotelId(HotelId hotelId) {
        return jpaRoomRepository.findByHotelIdAndIsAvailable(hotelId.getValue().toString(), true)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(RoomId id) {
        jpaRoomRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsByHotelIdAndRoomNumber(HotelId hotelId, String roomNumber) {
        return jpaRoomRepository.existsByHotelIdAndRoomNumber(hotelId.getValue().toString(), roomNumber);
    }

    private JpaRoomEntity toEntity(Room room) {
        return new JpaRoomEntity(
                room.getId().getValue(),
                room.getHotelId().getValue().toString(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getDescription(),
                room.getIsAvailable(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }

    private Room toDomain(JpaRoomEntity entity) {
        return new Room(
                new RoomId(entity.getId()),
                HotelId.from(entity.getHotelId()),
                entity.getRoomNumber(),
                entity.getRoomType(),
                entity.getCapacity(),
                entity.getPricePerNight(),
                entity.getDescription(),
                entity.getIsAvailable()
        );
    }
}
