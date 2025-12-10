package com.example.hotels_ms.application.dto;

import com.example.hotels_ms.domain.entities.Hotel;
import java.time.LocalDateTime;

public record HotelResponse(
    String id,
    String name,
    String description,
    String address,
    String city,
    String country,
    Integer stars,
    Double pricePerNight,
    Integer totalRooms,
    Integer availableRooms,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static HotelResponse from(Hotel hotel) {
        return new HotelResponse(
            hotel.getId().toString(),
            hotel.getName(),
            hotel.getDescription(),
            hotel.getAddress(),
            hotel.getCity(),
            hotel.getCountry(),
            hotel.getStars(),
            hotel.getPricePerNight(),
            hotel.getTotalRooms(),
            hotel.getAvailableRooms(),
            hotel.getCreatedAt(),
            hotel.getUpdatedAt()
        );
    }
}
