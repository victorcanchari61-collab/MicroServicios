package com.example.hotels_ms.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaRoomRepository extends JpaRepository<JpaRoomEntity, String> {
    List<JpaRoomEntity> findByHotelId(String hotelId);
    List<JpaRoomEntity> findByHotelIdAndIsAvailable(String hotelId, Boolean isAvailable);
    boolean existsByHotelIdAndRoomNumber(String hotelId, String roomNumber);
}
