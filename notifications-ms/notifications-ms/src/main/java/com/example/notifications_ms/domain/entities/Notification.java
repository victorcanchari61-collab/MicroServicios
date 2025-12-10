package com.example.notifications_ms.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String recipientEmail;
    private String recipientPhone;
    private String subject;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private String reservationId;
    private String eventType; // ReservationCreated, UserRegistered, etc.
    private int retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String errorMessage;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
        this.type = NotificationType.EMAIL;
        this.retryCount = 0;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsFailed(String error) {
        this.status = NotificationStatus.FAILED;
        this.errorMessage = error;
    }

    public void incrementRetryCount() {
        this.retryCount++;
    }
}

enum NotificationType {
    EMAIL, WHATSAPP, SMS
}

enum NotificationStatus {
    PENDING, SENT, FAILED
}
