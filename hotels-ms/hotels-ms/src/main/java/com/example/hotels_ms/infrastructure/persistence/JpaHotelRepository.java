package com.example.hotels_ms.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaHotelRepository extends JpaRepository<JpaHotelEntity, UUID> {
    List<JpaHotelEntity> findByCity(String city);
    List<JpaHotelEntity> findByCountry(String country);
}
