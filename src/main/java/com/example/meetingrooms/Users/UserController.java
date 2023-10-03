package com.example.meetingrooms.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository repo;

    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        System.out.println(user);

        System.out.println(user.getPassword());

        repo.save(user);

        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        //If there are no users
        if (repo.findAll().isEmpty()) {
            return new ResponseEntity<>("Repo is empty, add some users!", HttpStatus.OK);
        }
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        //If user attempted to be deleted does not exist
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("User not deleted; could not find user", HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);

        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }
}
