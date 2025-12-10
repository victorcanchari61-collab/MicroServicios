package com.example.notifications_ms.presentation.controllers;

import com.example.notifications_ms.application.dto.SendNotificationRequest;
import com.example.notifications_ms.application.usecases.SendNotificationUseCase;
import com.example.notifications_ms.domain.entities.Notification;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final SendNotificationUseCase sendNotificationUseCase;

    public NotificationController(SendNotificationUseCase sendNotificationUseCase) {
        this.sendNotificationUseCase = sendNotificationUseCase;
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        Notification notification = sendNotificationUseCase.execute(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Notifications service is running");
    }
}
