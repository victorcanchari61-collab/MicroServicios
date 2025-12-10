package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.HotelResponse;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import com.example.hotels_ms.domain.valueobjects.HotelId;
import org.springframework.stereotype.Service;

@Service
public class GetHotelByIdUseCase {
    private final HotelRepository hotelRepository;

    public GetHotelByIdUseCase(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse execute(String id) {
        HotelId hotelId = HotelId.from(id);
        
        return hotelRepository.findById(hotelId)
            .map(HotelResponse::from)
            .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }
}
