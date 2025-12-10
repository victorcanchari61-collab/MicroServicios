package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.HotelResponse;
import com.example.hotels_ms.application.dto.UpdateHotelRequest;
import com.example.hotels_ms.domain.entities.Hotel;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Service;

@Service
public class UpdateHotelUseCase {
    private final HotelRepository hotelRepository;

    public UpdateHotelUseCase(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse execute(String id, UpdateHotelRequest request) {
        HotelId hotelId = HotelId.from(id);
        
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        // Update hotel using domain methods (SOLID: Open/Closed Principle)
        hotel.updateInfo(
            request.name(),
            request.description(),
            request.address(),
            request.city(),
            request.country()
        );
        hotel.updatePricing(request.pricePerNight());
        hotel.updateRooms(request.totalRooms(), request.availableRooms());

        Hotel updatedHotel = hotelRepository.save(hotel);
        return HotelResponse.from(updatedHotel);
    }
}
