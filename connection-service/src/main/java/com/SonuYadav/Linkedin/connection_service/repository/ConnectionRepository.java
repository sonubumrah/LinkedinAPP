package com.SonuYadav.Linkedin.connection_service.repository;

import com.SonuYadav.Linkedin.connection_service.entity.Person;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("MATCH (personA:Person) -[:CONNECTED_TO]-(personB:Person) WHERE personA.userId = $userId RETURN personB")
    Optional<List<Person>> findFirstDegreeConnectionsByUserId(Long userId);

    @Query("MATCH (personA:Person) -[:CONNECTED_TO]-(personB:Person) -[:CONNECTED_TO]-(personC:Person) WHERE personA.userId = $userId AND NOT (personA)-[:CONNECTED_TO]-(personC) RETURN DISTINCT personC")
    Optional<List<Person>> findSecondDegreeConnectionsByUserId(Long userId);

    @Query("MATCH (personA:Person) -[:CONNECTED_TO]-(personB:Person) -[:CONNECTED_TO]-(personC:Person) -[:CONNECTED_TO]-(personD:Person) WHERE personA.userId = $userId AND NOT (personA)-[:CONNECTED_TO]-(personD) RETURN DISTINCT personD")
    Optional<List<Person>> findThirdDegreeConnectionByUserId(Long userId);

    @Query("MATCH (personA:Person) -[:CONNECTED_TO]-(personB:Person) WHERE personA.userId = $userId1 AND personB.userId = $userId2 RETURN EXISTS((personA)-[:CONNECTED_TO]-(personB))")
    Optional<Boolean> isAlreadyConnected(Long userId1, Long userId2);

    @Query("MATCH (sender:Person {userId: $senderUserId})-[:SENT_REQUEST]->(receiver:Person {userId: $receiverUserId}) RETURN EXISTS((sender)-[:SENT_REQUEST]->(receiver))")
    Optional<Boolean> isConnectionRequestAlreadySent(Long senderUserId, Long receiverUserId);

    @Query("MATCH (sender:Person {userId: $senderUserId})-[:SENT_REQUEST]->(receiver:Person {userId: $receiverUserId}) DELETE (sender)-[:SENT_REQUEST]->(receiver)")
    Optional<Boolean> addConnectionRequest(Long senderUserId, Long receiverUserId);

    @Query("MATCH (sender:Person {userId: $senderUserId})-[:SENT_REQUEST]->(receiver:Person {userId: $receiverUserId}) SET (sender)-[:CONNECTED_TO]->(receiver)")
    void acceptConnectionRequest(Long senderUserId, Long receiverUserId);

    @Query("MATCH (sender:Person {userId: $senderUserId})-[:SENT_REQUEST]->(receiver:Person {userId: $receiverUserId}) DELETE (sender)-[:SENT_REQUEST]->(receiver)")
    void rejectConnectionRequest(Long senderUserId, Long receiverUserId);
}
