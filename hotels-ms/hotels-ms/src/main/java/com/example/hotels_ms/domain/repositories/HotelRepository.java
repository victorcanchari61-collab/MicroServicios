package com.example.hotels_ms.domain.repositories;

import com.example.hotels_ms.domain.entities.Hotel;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import java.util.List;
import java.util.Optional;

public interface HotelRepository {
    Hotel save(Hotel hotel);
    Optional<Hotel> findById(HotelId id);
    List<Hotel> findAll();
    List<Hotel> findByCity(String city);
    List<Hotel> findByCountry(String country);
    void deleteById(HotelId id);
    boolean existsById(HotelId id);
}
