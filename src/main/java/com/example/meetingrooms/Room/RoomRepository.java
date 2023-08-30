package com.example.meetingrooms.Room;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "rooms", path = "rooms")
public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByName(@Param("name") String name);

}