package com.example.meetingrooms.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepo repo;

    @PostMapping("/users")
    public String saveUser(@RequestBody User user) {
        System.out.println(user);

        System.out.println(user.getPassword());

        repo.save(user);

        return "User added successfully";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return repo.findAll();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id) {
        repo.deleteById(id);

        return "User Deleted";
    }
}
