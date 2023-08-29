package com.example.meetingrooms.Meeting;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "meetings", path = "meetings")
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    List<Meeting> findByTitle(@Param("title") String title);
}
