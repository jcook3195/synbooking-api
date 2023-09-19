package com.example.meetingrooms.Meeting;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// import org.springframework.web.bind.annotation.CrossOrigin;

// @RepositoryRestResource(collectionResourceRel = "meetings", path = "meetings")
// @CrossOrigin("http://localhost:3000")
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    // List<Meeting> findByTitle(@Param("title") String title);
    List<Meeting> findByStartDateTime(@Param("startDateTime") String startDateTime);

    @Query("{startDateTime:{$regex:?0}}") // grabs all of the startDateTime value
    List<Meeting> findByStartDateRegEx(String startDateTime);

    @Query("{startDateTime:{$regex:?0}}")
    List<Meeting> findByStartTimeRegEx(String startDate);

    @Query("{room:?0},{startDateTime:{$regex:?0}}")
    List<Meeting> findByRoomDate(String room, Instant startDate);
}
