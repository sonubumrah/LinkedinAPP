package com.SonuYadav.Linkedin.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data

public class PostLikedEvent {
    private Long postId;
    private Long postLikedUserId;
    private Long postCreatedUserId;

}
