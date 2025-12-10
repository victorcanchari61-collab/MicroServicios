package com.example.notifications_ms.infrastructure.persistence;

import com.example.notifications_ms.domain.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByReservationId(String reservationId);
    List<Notification> findByRecipientEmail(String email);
}
