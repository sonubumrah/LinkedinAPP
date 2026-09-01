package com.SonuYadav.Linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data

public class AcceptConnectionRequest {
    private Long senderId;
    private Long receiverId;
}
