package com.SonuYadav.Linkedin.notification_service.client;
import com.SonuYadav.Linkedin.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="connection-service",path = "/connections")
@Component
public interface ConnectionsClient {


    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstDegreeConnections(@RequestHeader("X-User-Id") String userId);

}
