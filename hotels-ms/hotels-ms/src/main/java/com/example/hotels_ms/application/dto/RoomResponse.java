package com.example.hotels_ms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String id;
    private String hotelId;
    private String roomNumber;
    private String roomType;
    private Integer capacity;
    private Double pricePerNight;
    private String description;
    private Boolean isAvailable;
}
