package com.SonuYadav.Linkedin.notification_service.service;

import com.SonuYadav.Linkedin.notification_service.auth.UserContextHolder;
import com.SonuYadav.Linkedin.notification_service.client.ConnectionsClient;
import com.SonuYadav.Linkedin.notification_service.dto.PersonDto;
import com.SonuYadav.Linkedin.notification_service.entity.NotificationEntity;
import com.SonuYadav.Linkedin.notification_service.event.PostCreatedEvent;
import com.SonuYadav.Linkedin.notification_service.event.PostLikedEvent;
import com.SonuYadav.Linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumerService {
    private final ConnectionsClient connectionsClient;
    private final NotificationRepository notificationRepository;
    private final NotifcationService notifcationService;

    @KafkaListener(topics ="create-post-topic" )
    public void handleCreatePostEvent(PostCreatedEvent postCreatedEvent) {
        log.info("post created by user  {}", postCreatedEvent.getPostCreatedUserId());
        List<PersonDto> firstDegreeConnections = connectionsClient.getFirstDegreeConnections(UserContextHolder.getUserId());
        log.info("First degree connections:for  {}", postCreatedEvent.getPostCreatedUserId());

        for(PersonDto connection : firstDegreeConnections) {
            String message = "User " + postCreatedEvent.getPostCreatedUserId() + " created a new post.";
            notifcationService.sendNotificationToConnection(connection.getUserId(), message);
        }
    }
    @KafkaListener(topics = "like-post-topic")
    public void handlePostLikedEvent(PostLikedEvent postLikedEvent) {
        log.info("post liked by user  {}", postLikedEvent.getPostLikedUserId());
        String message = String.format("User %d liked your post with ID %d.", postLikedEvent.getPostLikedUserId(), postLikedEvent.getPostId());
        notifcationService.sendNotificationToConnection(postLikedEvent.getPostCreatedUserId(), message);
    }


}
