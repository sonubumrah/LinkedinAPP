package com.SonuYadav.Linkedin.connection_service.service;

import com.SonuYadav.Linkedin.connection_service.entity.Person;
import com.SonuYadav.Linkedin.connection_service.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

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
}
