package com.example.hotels_ms.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaRoomEntity {
    @Id
    private String id;
    
    @Column(name = "hotel_id", nullable = false)
    private String hotelId;
    
    @Column(name = "room_number", nullable = false)
    private String roomNumber;
    
    @Column(name = "room_type", nullable = false)
    private String roomType;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
