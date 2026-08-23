package com.SonuYadav.Linkedin.connection_service.repository;

import com.SonuYadav.Linkedin.connection_service.entity.Person;
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
}
