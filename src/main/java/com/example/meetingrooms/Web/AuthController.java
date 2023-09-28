package com.example.meetingrooms.Web;

import com.example.meetingrooms.DTO.AuthCredentialsRequest;
import com.example.meetingrooms.Users.User;
import com.example.meetingrooms.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Takes in requests for http path and authorizes
 * using username and password with generated token
**/
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired//allows for the authentication manager to apply
    private AuthenticationManager authenticationManager;

    @Autowired //allows access to the token functions
    private JwtUtil jwtUtil;

    @PostMapping("login") //Posts to login page and requests credentials
    public ResponseEntity <?> login (@RequestBody AuthCredentialsRequest request){
        try{
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            //user.setPassword(null);

            //returns the response, user information, and generates a token for the user
            return ResponseEntity.ok().header(
                    HttpHeaders.AUTHORIZATION,
                    jwtUtil.generateToken(user)
            ).body(user);
        } catch (BadCredentialsException ex){
            //returns unauthorized status if credentials are bad
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
