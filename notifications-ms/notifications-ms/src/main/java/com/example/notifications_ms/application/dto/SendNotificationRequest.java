package com.example.notifications_ms.application.dto;

import jakarta.validation.constraints.*;
import java.util.Map;

public record SendNotificationRequest(
    @NotBlank @Email String email,
    String phone,
    @NotBlank String subject,
    @NotBlank String eventType, // ReservationCreated, UserRegistered, etc.
    Map<String, String> variables, // Dynamic variables for templates
    String reservationId
) {}
