package com.SonuYadav.Linkedin.notification_service.service;

import com.SonuYadav.Linkedin.notification_service.entity.NotificationEntity;
import com.SonuYadav.Linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifcationService {
    private final NotificationRepository notificationRepository;
    public void sendNotificationToConnection(Long userId, String message) {
        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);

        log.info("Sending notification to user: {} for post created by user: {}", userId, message);
    }
}
