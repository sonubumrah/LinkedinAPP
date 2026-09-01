package com.SonuYadav.Linkedin.notification_service.service;

import com.SonuYadav.Linkedin.notification_service.event.AcceptConnectionRequest;
import com.SonuYadav.Linkedin.notification_service.event.SendConnectionRequest;
import com.SonuYadav.Linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    private final NotifcationService notifcationService;
    private final NotificationRepository notificationRepository;


    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequestEvent(AcceptConnectionRequest acceptConnectionRequest) {
        log.info("Connection request accepted by user  {}", acceptConnectionRequest.getReceiverId());
        String message = String.format("User %d accepted your connection request.", acceptConnectionRequest.getReceiverId());
        notifcationService.sendNotificationToConnection(acceptConnectionRequest.getSenderId(), message);
    }
    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequestEvent(SendConnectionRequest sendConnectionRequest) {
        log.info("Connection request sent by user  {}", sendConnectionRequest.getSenderId());
        String message = String.format("User %d sent you a connection request.", sendConnectionRequest.getSenderId());
        notifcationService.sendNotificationToConnection(sendConnectionRequest.getReceiverId(), message);
    }
}
