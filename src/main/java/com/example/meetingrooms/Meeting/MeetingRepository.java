package com.example.meetingrooms.Meeting;

import org.springframework.data.mongodb.repository.MongoRepository;

//@RepositoryRestResource(collectionResourceRel = "meetings", path = "meetings")
//@CrossOrigin("http://localhost:3000")
public interface MeetingRepository extends MongoRepository<Meeting, String> {
//    List<Meeting> findByTitle(@Param("title") String title);
}
