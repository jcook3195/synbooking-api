package com.example.meetingrooms.Meeting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MeetingController {
    @Autowired
    private MeetingRepository repo;

    @PostMapping("/meetings")
    public String saveMeeting(@RequestBody Meeting meeting) {
        System.out.println(meeting);

        System.out.println(meeting.getTitle());

        repo.save(meeting);

        return "Meeting added successfully";
    }

    @GetMapping("/meetings")
    public List<Meeting> getMeetings() {
        return repo.findAll();
    }

    @GetMapping("/meetings/{date}")
    public List<Meeting> getByDate(@PathVariable String date) {

        List<Meeting> meetings = new ArrayList<Meeting>();
        List<Meeting> allMeetings = repo.findAll();

        // Checks all meetings and compares to passed date to see if they match
        for (int i = 0; i < allMeetings.size(); i++) {
            if (allMeetings.get(i).getStartDateTime().substring(0, 10).equals(date)) {
                meetings.add(allMeetings.get(i));
            }
        }
        return meetings;
    }

    @DeleteMapping("/meeting/{id}")
    public String deletMeeting(@PathVariable String id) {
        repo.deleteById(id);

        return "Meeting Deleted";
    }
}
