package com.example.notifications_ms.domain.services;

import com.example.notifications_ms.domain.entities.Notification;
import com.example.notifications_ms.infrastructure.persistence.NotificationRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;
    private final NotificationRepository repository;
    private static final int MAX_RETRIES = 3;

    public NotificationService(JavaMailSender mailSender, NotificationRepository repository) {
        this.mailSender = mailSender;
        this.repository = repository;
    }

    @Async
    @Retryable(
        maxAttempts = MAX_RETRIES,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendEmail(Notification notification) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom("envios@apperpan.com");
            helper.setTo(notification.getRecipientEmail());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getMessage(), true); // true = HTML
            
            mailSender.send(mimeMessage);
            
            notification.markAsSent();
            notification.incrementRetryCount();
            repository.save(notification);
        } catch (Exception e) {
            notification.incrementRetryCount();
            if (notification.getRetryCount() >= MAX_RETRIES) {
                notification.markAsFailed(e.getMessage());
            }
            repository.save(notification);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
