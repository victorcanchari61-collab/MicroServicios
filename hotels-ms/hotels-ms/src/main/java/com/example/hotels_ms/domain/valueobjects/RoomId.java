package com.example.hotels_ms.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class RoomId {
    private final String value;

    public RoomId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("RoomId cannot be null or empty");
        }
        this.value = value;
    }

    public static RoomId generate() {
        return new RoomId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
