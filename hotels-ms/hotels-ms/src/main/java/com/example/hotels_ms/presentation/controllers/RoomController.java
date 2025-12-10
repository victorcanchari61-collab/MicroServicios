package com.example.hotels_ms.presentation.controllers;

import com.example.hotels_ms.application.dto.CreateRoomRequest;
import com.example.hotels_ms.application.dto.RoomResponse;
import com.example.hotels_ms.application.dto.UpdateRoomRequest;
import com.example.hotels_ms.application.usecases.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {
    private final CreateRoomUseCase createRoomUseCase;
    private final GetRoomsByHotelUseCase getRoomsByHotelUseCase;
    private final GetRoomByIdUseCase getRoomByIdUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;
    private final DeleteRoomUseCase deleteRoomUseCase;
    private final HoldRoomUseCase holdRoomUseCase;
    private final ReleaseRoomUseCase releaseRoomUseCase;

    public RoomController(
            CreateRoomUseCase createRoomUseCase,
            GetRoomsByHotelUseCase getRoomsByHotelUseCase,
            GetRoomByIdUseCase getRoomByIdUseCase,
            UpdateRoomUseCase updateRoomUseCase,
            DeleteRoomUseCase deleteRoomUseCase,
            HoldRoomUseCase holdRoomUseCase,
            ReleaseRoomUseCase releaseRoomUseCase
    ) {
        this.createRoomUseCase = createRoomUseCase;
        this.getRoomsByHotelUseCase = getRoomsByHotelUseCase;
        this.getRoomByIdUseCase = getRoomByIdUseCase;
        this.updateRoomUseCase = updateRoomUseCase;
        this.deleteRoomUseCase = deleteRoomUseCase;
        this.holdRoomUseCase = holdRoomUseCase;
        this.releaseRoomUseCase = releaseRoomUseCase;
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotel(@PathVariable String hotelId) {
        List<RoomResponse> rooms = getRoomsByHotelUseCase.execute(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        try {
            RoomResponse room = getRoomByIdUseCase.execute(id);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<RoomResponse> createRoom(
            @PathVariable String hotelId,
            @Valid @RequestBody CreateRoomRequest request
    ) {
        try {
            RoomResponse room = createRoomUseCase.execute(hotelId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(room);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable String id,
            @Valid @RequestBody UpdateRoomRequest request
    ) {
        try {
            RoomResponse room = updateRoomUseCase.execute(id, request);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        try {
            deleteRoomUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/rooms/{id}/hold")
    public ResponseEntity<Void> holdRoom(@PathVariable String id) {
        try {
            holdRoomUseCase.execute(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/rooms/{id}/release")
    public ResponseEntity<Void> releaseRoom(@PathVariable String id) {
        try {
            releaseRoomUseCase.execute(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
