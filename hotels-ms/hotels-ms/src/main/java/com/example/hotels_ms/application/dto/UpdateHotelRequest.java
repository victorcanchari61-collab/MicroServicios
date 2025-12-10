package com.example.hotels_ms.application.dto;

import jakarta.validation.constraints.*;

public record UpdateHotelRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255)
    String name,

    @Size(max = 1000)
    String description,

    @NotBlank(message = "Address is required")
    @Size(max = 500)
    String address,

    @NotBlank(message = "City is required")
    @Size(max = 100)
    String city,

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    String country,

    @NotNull(message = "Stars rating is required")
    @Min(1) @Max(5)
    Integer stars,

    @NotNull(message = "Price per night is required")
    @DecimalMin("0.01")
    Double pricePerNight,

    @NotNull(message = "Total rooms is required")
    @Min(1)
    Integer totalRooms,

    @NotNull(message = "Available rooms is required")
    @Min(0)
    Integer availableRooms
) {}
