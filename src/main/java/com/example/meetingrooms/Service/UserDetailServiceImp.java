package com.example.meetingrooms.Service;

import com.example.meetingrooms.Users.User;
import com.example.meetingrooms.Users.UserRepo;
import com.example.meetingrooms.Utility.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //service file for getting user details (Spring)
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired //allows for calling custom password
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override //override function to get user by username (hard coded)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOpt = userRepo.findByUsername(username);

        return userOpt.orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword("{bcrypt}" + passwordEncoder.getPasswordEncoder().encode("123"));
//        user.setId("1");
//        System.out.println("password" + user.getPassword());
//
//        return user;
    }
}
