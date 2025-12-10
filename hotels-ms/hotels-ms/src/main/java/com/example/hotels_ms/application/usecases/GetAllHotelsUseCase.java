package com.example.hotels_ms.application.usecases;

import com.example.hotels_ms.application.dto.HotelResponse;
import com.example.hotels_ms.domain.repositories.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllHotelsUseCase {
    private final HotelRepository hotelRepository;

    public GetAllHotelsUseCase(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelResponse> execute() {
        return hotelRepository.findAll()
            .stream()
            .map(HotelResponse::from)
            .collect(Collectors.toList());
    }
}
