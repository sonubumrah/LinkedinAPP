package com.SonuYadav.Linkedin.Post_Service.client;

import com.SonuYadav.Linkedin.Post_Service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="connection-service",path = "/connections")
@Component
public interface ConnectionsClient {


    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstDegreeConnections();

}
