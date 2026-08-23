package com.SonuYadav.Linkedin.Post_Service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {
    private Long postId;
    private Long postCreatedUserId;
    private String postContent;
}
