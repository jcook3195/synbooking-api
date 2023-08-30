package com.example.meetingrooms.Room;

import org.springframework.data.annotation.Id;

public class Room {

    @Id
    private String id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
