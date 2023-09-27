package com.example.meetingrooms.Web;

import com.example.meetingrooms.DTO.AuthCredentialsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("login")
    public ResponseEntity <?> login (AuthCredentialsRequest request){
        return ResponseEntity.ok(null);
    }
}
