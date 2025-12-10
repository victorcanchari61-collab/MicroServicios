package com.example.hotels_ms.presentation.controllers;

import com.example.hotels_ms.application.dto.CreateHotelRequest;
import com.example.hotels_ms.application.dto.HotelResponse;
import com.example.hotels_ms.application.usecases.CreateHotelUseCase;
import com.example.hotels_ms.application.usecases.GetAllHotelsUseCase;
import com.example.hotels_ms.application.usecases.GetHotelByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final CreateHotelUseCase createHotelUseCase;
    private final GetAllHotelsUseCase getAllHotelsUseCase;
    private final GetHotelByIdUseCase getHotelByIdUseCase;
    private final com.example.hotels_ms.application.usecases.UpdateHotelUseCase updateHotelUseCase;
    private final com.example.hotels_ms.application.usecases.DeleteHotelUseCase deleteHotelUseCase;

    public HotelController(
        CreateHotelUseCase createHotelUseCase,
        GetAllHotelsUseCase getAllHotelsUseCase,
        GetHotelByIdUseCase getHotelByIdUseCase,
        com.example.hotels_ms.application.usecases.UpdateHotelUseCase updateHotelUseCase,
        com.example.hotels_ms.application.usecases.DeleteHotelUseCase deleteHotelUseCase
    ) {
        this.createHotelUseCase = createHotelUseCase;
        this.getAllHotelsUseCase = getAllHotelsUseCase;
        this.getHotelByIdUseCase = getHotelByIdUseCase;
        this.updateHotelUseCase = updateHotelUseCase;
        this.deleteHotelUseCase = deleteHotelUseCase;
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotels = getAllHotelsUseCase.execute();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable String id) {
        try {
            HotelResponse hotel = getHotelByIdUseCase.execute(id);
            return ResponseEntity.ok(hotel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@RequestBody @jakarta.validation.Valid CreateHotelRequest request) {
        HotelResponse hotel = createHotelUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(
        @PathVariable String id,
        @RequestBody @jakarta.validation.Valid com.example.hotels_ms.application.dto.UpdateHotelRequest request
    ) {
        HotelResponse hotel = updateHotelUseCase.execute(id, request);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        deleteHotelUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
