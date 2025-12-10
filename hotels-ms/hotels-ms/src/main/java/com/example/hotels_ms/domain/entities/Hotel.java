package com.example.hotels_ms.domain.entities;

import com.example.hotels_ms.domain.valueobjects.HotelId;
import java.time.LocalDateTime;
import java.util.UUID;

public class Hotel {
    private HotelId id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private Integer stars;
    private Double pricePerNight;
    private Integer totalRooms;
    private Integer availableRooms;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Hotel(
            HotelId id,
            String name,
            String description,
            String address,
            String city,
            String country,
            Integer stars,
            Double pricePerNight,
            Integer totalRooms,
            Integer availableRooms
    ) {
        // Domain validations (SOLID: Single Responsibility - Entity validates itself)
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be empty");
        }
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (totalRooms < 1) {
            throw new IllegalArgumentException("Total rooms must be at least 1");
        }
        if (availableRooms < 0) {
            throw new IllegalArgumentException("Available rooms cannot be negative");
        }
        if (availableRooms > totalRooms) {
            throw new IllegalArgumentException("Available rooms cannot exceed total rooms");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.stars = stars;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public HotelId getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public Integer getStars() { return stars; }
    public Double getPricePerNight() { return pricePerNight; }
    public Integer getTotalRooms() { return totalRooms; }
    public Integer getAvailableRooms() { return availableRooms; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Business logic
    public void updateInfo(String name, String description, String address, String city, String country) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePricing(Double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.pricePerNight = pricePerNight;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateRooms(Integer totalRooms, Integer availableRooms) {
        if (availableRooms > totalRooms) {
            throw new IllegalArgumentException("Available rooms cannot exceed total rooms");
        }
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasAvailableRooms() {
        return availableRooms > 0;
    }
}
