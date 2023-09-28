package com.example.meetingrooms.Meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class MeetingController {
    @Autowired
    private MeetingRepository repo;

    @PostMapping("/meetings")
    public ResponseEntity<String> saveMeeting(@RequestBody Meeting meeting) {
        // gets meeting as object (NOT saved into repo) and allows changes
        System.out.println(meeting);
        System.out.println(meeting.getTitle()); // does the regex have an issue with the partial times
        boolean validate = validateMeeting(meeting, null);

        // saves meeting to repo and returns message into Postman
        if (validate) {
            repo.save(meeting);
            return new ResponseEntity<>("Meeting added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Meeting not added due to conflict", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/meetings/{id}")
    public ResponseEntity<String> updateMeeting(@PathVariable String id, @RequestBody Meeting meeting) {
        // updates meeting by overwriting old one with new one; requires id to be passed from frontend
        //Not happy about this  -Jordan :(
        boolean validate = validateMeeting(meeting, id);

        // saves meeting to repo after confirming no conflicts
        System.out.println(validate);
        if (validate) {
            repo.save(meeting);
            return new ResponseEntity<>("Meeting edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Meeting not edited due to conflict", HttpStatus.BAD_REQUEST);
    }

    public boolean validateMeeting(Meeting meeting, String id) {
        String room = meeting.getRoom();
        boolean valid = false;
        Instant startDateTime = null;
        startDateTime = Instant.parse(meeting.getStartDateTime());

        Instant endDateTime = null;
        endDateTime = Instant.parse(meeting.getEndDateTime());

        List<Meeting> meetings = repo.findByRoomDate(room, startDateTime); // can get start/end times from here
        if (meetings.isEmpty()) {
            valid = true;
            return valid;
        }

        for (int i = 0; i < meetings.size(); i++) {
            Instant compStart = null;
            compStart = Instant.parse(meetings.get(i).getStartDateTime());

            Instant compEnd = null;
            compEnd = Instant.parse(meetings.get(i).getEndDateTime());

            // Is used when updating a meeting, insuring a false flag doesn't occur by
            // comparing
            // to the meeting being updated to itself
            String idCheck = meeting.getId();
            if (meetings.get(i).getId().equals(meeting.getId()) || meetings.get(i).getId().equals(id)) {
                continue;
            }

            // rejects any meetings that overlap currently scheduled meetings times by
            // checking if new start time is at or after current meeting's AND new start
            // time is before
            // current meeting ends OR new end time is after current start time AND new end
            // is before or at
            // current end time OR if the meetings share start or end times
            if ((!(startDateTime.isBefore(compStart)) && (startDateTime.isBefore(compEnd))) ||
                    (!(endDateTime.isBefore(compStart)) && endDateTime.isBefore(compEnd)) ||
                    startDateTime.equals(compStart) || (endDateTime.equals(compEnd))) {

                valid = false;
                break;
            } else {
                valid = true;
            }
        }
        return valid;
    }

    @GetMapping("/meetings")
    public ResponseEntity<?> getMeetings() {
        //If there are no users
        if (repo.findAll().isEmpty()){
            return new ResponseEntity<>("Repo is empty, add some meetings!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("meetings/{date}")
    public ResponseEntity<?> getByDate(@PathVariable String date) {
        List<Meeting> meetings = repo.findByStartDateRegEx(date);

        //If there are no meetings on that date
        if (meetings.isEmpty()) {
            return new ResponseEntity<>("No meetings on that date found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @GetMapping("meetings/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<Meeting> meeting = repo.findById(id);

        //If there is no meeting with that ID
        if (meeting.isEmpty()) {
            return new ResponseEntity<>("No meeting with that ID found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<String> deletMeeting(@PathVariable String id) {
        //If meeting attempted to be deleted does not exist
        if(!repo.existsById(id)) {
           return new ResponseEntity<>("Meeting not deleted; could not find meeting", HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);

        return new ResponseEntity<>("Meeting deleted", HttpStatus.OK);
    }
}
