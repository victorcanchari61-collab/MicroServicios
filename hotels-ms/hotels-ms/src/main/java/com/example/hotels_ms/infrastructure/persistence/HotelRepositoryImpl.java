package com.example.hotels_ms.infrastructure.persistence;

import com.example.hotels_ms.domain.entities.Hotel;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HotelRepositoryImpl implements HotelRepository {
    private final JpaHotelRepository jpaRepository;

    public HotelRepositoryImpl(JpaHotelRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Hotel save(Hotel hotel) {
        JpaHotelEntity entity = toEntity(hotel);
        JpaHotelEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Hotel> findById(HotelId id) {
        return jpaRepository.findById(id.getValue())
            .map(this::toDomain);
    }

    @Override
    public List<Hotel> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Hotel> findByCity(String city) {
        return jpaRepository.findByCity(city)
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Hotel> findByCountry(String country) {
        return jpaRepository.findByCountry(country)
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(HotelId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(HotelId id) {
        return jpaRepository.existsById(id.getValue());
    }

    private JpaHotelEntity toEntity(Hotel hotel) {
        JpaHotelEntity entity = new JpaHotelEntity();
        entity.setId(hotel.getId().getValue());
        entity.setName(hotel.getName());
        entity.setDescription(hotel.getDescription());
        entity.setAddress(hotel.getAddress());
        entity.setCity(hotel.getCity());
        entity.setCountry(hotel.getCountry());
        entity.setStars(hotel.getStars());
        entity.setPricePerNight(hotel.getPricePerNight());
        entity.setTotalRooms(hotel.getTotalRooms());
        entity.setAvailableRooms(hotel.getAvailableRooms());
        entity.setCreatedAt(hotel.getCreatedAt());
        entity.setUpdatedAt(hotel.getUpdatedAt());
        return entity;
    }

    private Hotel toDomain(JpaHotelEntity entity) {
        return new Hotel(
            new HotelId(entity.getId()),
            entity.getName(),
            entity.getDescription(),
            entity.getAddress(),
            entity.getCity(),
            entity.getCountry(),
            entity.getStars(),
            entity.getPricePerNight(),
            entity.getTotalRooms(),
            entity.getAvailableRooms()
        );
    }
}
