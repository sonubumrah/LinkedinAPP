package com.SonuYadav.Linkedin.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class SendConnectionRequest {
    private Long senderId;
    private Long receiverId;
}
