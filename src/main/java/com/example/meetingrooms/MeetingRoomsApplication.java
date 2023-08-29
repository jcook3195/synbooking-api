package com.example.meetingrooms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// test comment
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MeetingRoomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomsApplication.class, args);
	}

}
