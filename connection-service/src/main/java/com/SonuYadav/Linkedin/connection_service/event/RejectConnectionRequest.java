package com.SonuYadav.Linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data

public class RejectConnectionRequest {
    private Long senderId;
    private Long receiverId;
}
