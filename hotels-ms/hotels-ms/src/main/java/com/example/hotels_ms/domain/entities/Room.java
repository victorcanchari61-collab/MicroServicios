package com.example.hotels_ms.domain.entities;

import com.example.hotels_ms.domain.valueobjects.HotelId;
import com.example.hotels_ms.domain.valueobjects.RoomId;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Room {
    private final RoomId id;
    private final HotelId hotelId;
    private String roomNumber;
    private String roomType; // SINGLE, DOUBLE, SUITE, DELUXE
    private Integer capacity;
    private Double pricePerNight;
    private String description;
    private Boolean isAvailable;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room(
            RoomId id,
            HotelId hotelId,
            String roomNumber,
            String roomType,
            Integer capacity,
            Double pricePerNight,
            String description,
            Boolean isAvailable
    ) {
        validateInputs(roomNumber, roomType, capacity, pricePerNight);
        
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.isAvailable = isAvailable != null ? isAvailable : true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateInputs(String roomNumber, String roomType, Integer capacity, Double pricePerNight) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }

    public void updateInfo(String roomNumber, String roomType, Integer capacity, String description) {
        validateInputs(roomNumber, roomType, capacity, this.pricePerNight);
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePrice(Double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.pricePerNight = pricePerNight;
        this.updatedAt = LocalDateTime.now();
    }

    public void setAvailability(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        this.updatedAt = LocalDateTime.now();
    }

    public void hold() {
        this.isAvailable = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void release() {
        this.isAvailable = true;
        this.updatedAt = LocalDateTime.now();
    }
}
