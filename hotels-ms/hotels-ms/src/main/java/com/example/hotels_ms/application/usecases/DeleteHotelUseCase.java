package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Service;

@Service
public class DeleteHotelUseCase {
    private final HotelRepository hotelRepository;

    public DeleteHotelUseCase(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public void execute(String id) {
        HotelId hotelId = HotelId.from(id);
        
        if (!hotelRepository.existsById(hotelId)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }

        hotelRepository.deleteById(hotelId);
    }
}
