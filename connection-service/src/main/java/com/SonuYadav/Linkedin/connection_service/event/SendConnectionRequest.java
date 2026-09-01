package com.SonuYadav.Linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data

public class SendConnectionRequest {
    private Long senderId;
    private Long receiverId;
}
