package com.SonuYadav.Linkedin.connection_service.controller;

import com.SonuYadav.Linkedin.connection_service.auth.UserContextHolder;
import com.SonuYadav.Linkedin.connection_service.entity.Person;
import com.SonuYadav.Linkedin.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/core")
public class ConnectionController {
    private final ConnectionService connectionService;

    @GetMapping("/first-degree")
    ResponseEntity<List<Person>> getFirstDegreeConnections() {
        Long userId= Long.parseLong(UserContextHolder.getUserId());
        log.info("Get first degree connections endpoint called for userId: {}", userId);
        List<Person> firstDegreeConnections = connectionService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(firstDegreeConnections);
    }
    @GetMapping("/{userId}/second-degree")
    ResponseEntity<List<Person>> getSecondDegreeConnections(@PathVariable Long userId) {
        log.info("Get second degree connections endpoint called for userId: {}", userId);
        List<Person> secondDegreeConnections = connectionService.getSecondDegreeConnections(userId);
        return ResponseEntity.ok(secondDegreeConnections);
    }
    @GetMapping("/{userId}/third-degree")
    ResponseEntity<List<Person>> getThirdDegreeConnections(@PathVariable Long userId) {
        log.info("Get third degree connections endpoint called for userId: {}", userId);
        List<Person> thirdDegreeConnections = connectionService.getThirdDegreeConnections(userId);
        return ResponseEntity.ok(thirdDegreeConnections);
    }
    @PostMapping("/request/{receiverUserId}")
    ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long receiverUserId) {
        Long senderUserId = Long.parseLong(UserContextHolder.getUserId());
        log.info("Send connection request endpoint called for senderUserId: {}, receiverUserId: {}", senderUserId, receiverUserId);
        Boolean requestSent = connectionService.sendConnectionRequest(senderUserId, receiverUserId);
        return ResponseEntity.ok(requestSent);
    }
    @PostMapping("/accept/{senderUserId}")
    ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long senderUserId) {
        Long receiverUserId = Long.parseLong(UserContextHolder.getUserId());
        log.info("Accept connection request endpoint called for senderUserId: {}, receiverUserId: {}", senderUserId, receiverUserId);
        connectionService.acceptConnectionRequest(senderUserId, receiverUserId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/reject/{senderUserId}")
    ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long senderUserId) {
        Long receiverUserId = Long.parseLong(UserContextHolder.getUserId());
        log.info("Reject connection request endpoint called for senderUserId: {}, receiverUserId: {}", senderUserId, receiverUserId);
        connectionService.rejectConnectionRequest(senderUserId, receiverUserId);
        return ResponseEntity.ok().build();
    }
}
