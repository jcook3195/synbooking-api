package com.example.meetingrooms.Users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {

    //void findAll(String username);

    Optional<User> findByUsername(@Param("username") String username);
}
