package com.example.meetingrooms.Utility;

import com.example.meetingrooms.Users.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * This is a filter for jwt.
 * This is an authentication checking for a correct
 * JSON Web Token header or "bearer"
**/

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired //access user repository
    private UserRepo userRepo;
    @Autowired //access jwt utility functions
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        //This checks for if the authorization header is correct
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || StringUtils.hasText(header) && !header.startsWith("bearer ")){
            chain.doFilter(request, response);
            return;
        }
        //Splits the token from the tag and keeping the token value
        final String token = header.split(" ")[1].trim();

        //grabs the username via token and compares if they are the same
        UserDetails userDetails = userRepo
                .findByUsername(jwtUtil.getUsernameFromToken(token))
                .orElse(null);

        if(!jwtUtil.validateToken(token, userDetails)){
            chain.doFilter(request, response);
            return;
        }

        //Creates new password authentication token that stores user details and authorities
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        //sets context to the proper authorization and sends response to next filter
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }
}
