package com.SonuYadav.Linkedin.Post_Service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikedEvent {
    private Long postId;
    private Long postLikedUserId;
    private Long postCreatedUserId;

}
