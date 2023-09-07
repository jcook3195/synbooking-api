package com.example.meetingrooms.Room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "rooms", path = "rooms")
@CrossOrigin("http://localhost:3000")
public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByName(@Param("name") String name);

}