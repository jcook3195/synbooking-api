package com.example.meetingrooms.Users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    //Query to find account by a username to pull information
    Optional<User> findByUsername(@Param("username") String username);
}
