package com.example.hotels_ms.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class HotelId {
    private final UUID value;

    public HotelId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Hotel ID cannot be null");
        }
        this.value = value;
    }

    public static HotelId generate() {
        return new HotelId(UUID.randomUUID());
    }

    public static HotelId from(String value) {
        return new HotelId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelId hotelId = (HotelId) o;
        return Objects.equals(value, hotelId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
