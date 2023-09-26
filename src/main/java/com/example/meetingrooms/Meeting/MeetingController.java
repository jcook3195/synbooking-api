package com.example.meetingrooms.Meeting;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:3000")
public class MeetingController {
    @Autowired
    private MeetingRepository repo;

    @PostMapping("/meetings")
    public String saveMeeting(@RequestBody Meeting meeting) {
        // gets meeting as object (NOT saved into repo) and allows changes
        System.out.println(meeting);
        System.out.println(meeting.getTitle()); // does the regex have an issue with the partial times
        boolean validate = validateMeeting(meeting);

        // saves meeting to repo and returns message into Postman
        if (validate) {
            repo.save(meeting);
            return "Meeting added successfully";
        }
        return "Meeting not added due to conflict";
    }

    @PutMapping("/meetings/{id}")
    public String updateMeeting(@RequestBody Meeting meeting) {
        // updates meeting by overwriting old one with new one
        boolean validate = validateMeeting(meeting);

        // saves meeting to repo after confirming no conflicts
        if (validate) {
            repo.save(meeting);
            return "Meeting edited successfully";
        }
        return "Meeting not edited due to conflict";
    }

    @PostMapping("meetings/")
    public boolean validateMeeting(@RequestBody Meeting meeting) {
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
            // the meeting being updated to itself
            if (meetings.get(i).getId().equals(meeting.getId())) {
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
    public List<Meeting> getMeetings() {
        return repo.findAll();
    }

    @GetMapping("meetings/{date}") // EDIT? change to /meetings ?
    public List<Meeting> getByDate(@PathVariable String date) {
        List<Meeting> meetings = repo.findByStartDateRegEx(date);

        return meetings;
    }

    @DeleteMapping("/meetings/{id}")
    public String deletMeeting(@PathVariable String id) {
        repo.deleteById(id);

        return "Meeting Deleted";
    }
}
