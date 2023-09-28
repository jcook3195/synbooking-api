package com.example.meetingrooms.Config;

import com.example.meetingrooms.Utility.CustomPasswordEncoder;
import com.example.meetingrooms.Utility.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


import static org.springframework.security.config.Customizer.withDefaults;
/**Main Security Configuration File**/

@EnableWebSecurity
@Configuration
@CrossOrigin("http://localhost:3000")
public class SecurityConfig {

    @Autowired //allows for user details
    private UserDetailsService userDetailsService;

    @Autowired //allows for custom password encoder
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired //allows for jwt token filter
    private JwtFilter jwtFilter;

    @Bean //used for authorization controller to get the authorizationManager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    //Method for creating authentications using custom password encoder
    protected void Authentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    //"Driver" of all security features
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        //enables default cors and disables csrf
        http = http.cors(withDefaults()).csrf((csrf)-> csrf.disable());

        //creates a stateless session for the authentication management
        http = http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //exception handling to ensure that the proper message is used for
        http = http.exceptionHandling((exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        })));

        //permits requests to /api/auth/login requires authorization for any other request
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());

        //adds the jwt filter before http call
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}
