package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.CreateHotelRequest;
import com.example.hotels_ms.application.dto.HotelResponse;
import com.example.hotels_ms.domain.entities.Hotel;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Service;

@Service
public class CreateHotelUseCase {
    private final HotelRepository hotelRepository;

    public CreateHotelUseCase(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse execute(CreateHotelRequest request) {
        // Create hotel entity
        Hotel hotel = new Hotel(
            HotelId.generate(),
            request.name(),
            request.description(),
            request.address(),
            request.city(),
            request.country(),
            request.stars(),
            request.pricePerNight(),
            request.totalRooms(),
            request.availableRooms()
        );

        // Save hotel
        Hotel savedHotel = hotelRepository.save(hotel);

        return HotelResponse.from(savedHotel);
    }
}
