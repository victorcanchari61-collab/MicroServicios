package com.example.hotels_ms.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    
    @NotBlank(message = "Room type is required")
    private String roomType;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double pricePerNight;
    
    private String description;
}
