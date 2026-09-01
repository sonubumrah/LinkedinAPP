package com.SonuYadav.Linkedin.connection_service.service;

import com.SonuYadav.Linkedin.connection_service.entity.Person;
import com.SonuYadav.Linkedin.connection_service.event.AcceptConnectionRequest;
import com.SonuYadav.Linkedin.connection_service.event.RejectConnectionRequest;
import com.SonuYadav.Linkedin.connection_service.event.SendConnectionRequest;
import com.SonuYadav.Linkedin.connection_service.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final KafkaTemplate<Long, SendConnectionRequest> sendConnectionRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequest> acceptConnectionRequestKafkaTemplate;
    private final KafkaTemplate<Long, RejectConnectionRequest> rejectConnectionRequestKafkaTemplate;

    public List<Person> getFirstDegreeConnections(Long userId) {
        return connectionRepository.findFirstDegreeConnectionsByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No first degree connections found for userId: " + userId));
    }

    public List<Person> getSecondDegreeConnections(Long userId) {
        return connectionRepository.findSecondDegreeConnectionsByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No second degree connections found for userId: " + userId));
    }

    public List<Person> getThirdDegreeConnections(Long userId) {
        return connectionRepository.findThirdDegreeConnectionByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No third degree connections found for userId: " + userId));
    }

    public Boolean sendConnectionRequest(Long senderUserId, Long receiverUserId) {
        log.info("Checking if connection request already sent from userId: {} to userId: {}", senderUserId, receiverUserId);
        Boolean isRequestAlreadySent = connectionRepository.isConnectionRequestAlreadySent(senderUserId, receiverUserId)
                .orElse(false);

        if (isRequestAlreadySent) {
            throw new RuntimeException("Connection request already sent from userId: " + senderUserId + " to userId: " + receiverUserId);
        }
        Boolean isAlreadyConnected = connectionRepository.isAlreadyConnected(senderUserId, receiverUserId)
                .orElse(false);
        if (isAlreadyConnected) {
            throw new RuntimeException("Users are already connected: userId: " + senderUserId + " and userId: " + receiverUserId);
        }

        log.info("Sending connection request from userId: {} to userId: {}", senderUserId, receiverUserId);
        connectionRepository.addConnectionRequest(senderUserId, receiverUserId);
        SendConnectionRequest sendConnectionRequest= SendConnectionRequest.builder()
                .senderId(senderUserId)
                .receiverId(receiverUserId)
                .build();
        sendConnectionRequestKafkaTemplate.send("send-connection-request", sendConnectionRequest);
        return true;
    }

    public void acceptConnectionRequest(Long senderUserId, Long receiverUserId) {
        Boolean isRequestAlreadySent = connectionRepository.isConnectionRequestAlreadySent(senderUserId, receiverUserId)
                .orElse(false);
        if (!isRequestAlreadySent) {
            throw new RuntimeException("No connection request found from userId: " + senderUserId + " to userId: " + receiverUserId);
        }
        log.info("Accepting connection request from userId: {} to userId: {}", senderUserId, receiverUserId);
        AcceptConnectionRequest acceptConnectionRequest = AcceptConnectionRequest.builder()
                .senderId(senderUserId)
                .receiverId(receiverUserId)
                .build();
        acceptConnectionRequestKafkaTemplate.send("accept-connection-request", acceptConnectionRequest);
        connectionRepository.acceptConnectionRequest(senderUserId, receiverUserId);
    }

    public void rejectConnectionRequest(Long senderUserId, Long receiverUserId) {
        Boolean isRequestAlreadySent = connectionRepository.isConnectionRequestAlreadySent(senderUserId, receiverUserId)
                .orElse(false);
        if (!isRequestAlreadySent) {
            throw new RuntimeException("No connection request found from userId: " + senderUserId + " to userId: " + receiverUserId);
        }

        log.info("Rejecting connection request from userId: {} to userId: {}", senderUserId, receiverUserId);
        connectionRepository.rejectConnectionRequest(senderUserId, receiverUserId);
        RejectConnectionRequest rejectConnectionRequest = RejectConnectionRequest.builder()
                .senderId(senderUserId)
                .receiverId(receiverUserId)
                .build();
        rejectConnectionRequestKafkaTemplate.send("reject-connection-request", rejectConnectionRequest);

    }
}