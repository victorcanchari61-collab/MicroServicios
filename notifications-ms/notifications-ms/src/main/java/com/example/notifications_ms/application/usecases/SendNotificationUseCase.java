package com.example.notifications_ms.application.usecases;

import com.example.notifications_ms.application.dto.SendNotificationRequest;
import com.example.notifications_ms.domain.entities.Notification;
import com.example.notifications_ms.domain.services.EmailTemplateService;
import com.example.notifications_ms.domain.services.NotificationService;
import com.example.notifications_ms.infrastructure.persistence.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SendNotificationUseCase {
    private final NotificationRepository repository;
    private final NotificationService notificationService;
    private final EmailTemplateService templateService;

    public SendNotificationUseCase(
        NotificationRepository repository,
        NotificationService notificationService,
        EmailTemplateService templateService
    ) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.templateService = templateService;
    }

    public Notification execute(SendNotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientEmail(request.email());
        notification.setRecipientPhone(request.phone());
        notification.setSubject(request.subject());
        notification.setReservationId(request.reservationId());
        notification.setEventType(request.eventType());
        
        // Apply template based on event type
        String htmlMessage = applyTemplate(request.eventType(), request.variables());
        notification.setMessage(htmlMessage);
        
        // Send notification asynchronously with retry (skip MongoDB save for now)
        try {
            notificationService.sendEmail(notification);
            notification.markAsSent();
        } catch (Exception e) {
            notification.markAsFailed(e.getMessage());
        }
        
        // Try to save to MongoDB (optional, won't fail if MongoDB has issues)
        try {
            notification = repository.save(notification);
        } catch (Exception e) {
            // Log but don't fail - email is more important than history
            System.err.println("Failed to save notification to MongoDB: " + e.getMessage());
        }
        
        return notification;
    }

    private String applyTemplate(String eventType, Map<String, String> variables) {
        return switch (eventType) {
            case "ReservationCreated" -> templateService.getReservationConfirmationTemplate(variables);
            case "ReservationCancelled" -> templateService.getReservationCancellationTemplate(variables);
            case "UserRegistered" -> templateService.getWelcomeTemplate(variables);
            default -> variables.getOrDefault("message", "Notification");
        };
    }
}
