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

/*Used for getting user details*/

@Service //service file for getting user details (Spring)
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired //allows for calling custom password
    private CustomPasswordEncoder passwordEncoder;

    @Autowired //allows access to the user repository
    private UserRepo userRepo;

    @Override //override function to get user by username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //may or may not return username
        Optional<User> userOpt = userRepo.findByUsername(username);

        //returns username or if not found "Invalid Username"
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));
    }
}
