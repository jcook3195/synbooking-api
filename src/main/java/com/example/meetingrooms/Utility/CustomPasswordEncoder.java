package com.example.meetingrooms.Utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Custom password encoder component using BCrypt
**/
@Component
public class CustomPasswordEncoder {
    private PasswordEncoder passwordEncoder;

    public CustomPasswordEncoder(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
