package com.SonuYadav.Linkedin.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostCreatedEvent {
    private Long postId;
    private Long postCreatedUserId;
    private String postContent;
}
