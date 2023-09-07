package com.example.meetingrooms.Meeting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "meetings", path = "meetings")
@CrossOrigin("http://localhost:3000")
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    List<Meeting> findByTitle(@Param("title") String title);
}
