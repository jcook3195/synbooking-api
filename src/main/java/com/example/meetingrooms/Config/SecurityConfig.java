package com.example.meetingrooms.Config;

import com.example.meetingrooms.Utility.CustomPasswordEncoder;
import com.example.meetingrooms.Utility.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@CrossOrigin("http://localhost:3000")
public class SecurityConfig {

    @Autowired //allows for user details
    private UserDetailsService userDetailsService;

    @Autowired //allows for custom password encoder
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private JwtFilter jwtFilter;

    protected void Authentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
//        //Default Requests
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//
//        //Default Form Login On Page
//        //http.formLogin(withDefaults());
//
//        //Default browser implement
//        http.httpBasic(withDefaults());
//        //http.csrf((csrf) -> csrf.disable()); //Disables the csrf tokens
//        return http.build();

//        //Authorize Everything
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
//        http.httpBasic(withDefaults());
//        return http.build();

        //Authorize specific paths, deny all other paths
        //http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/meetings").permitAll().anyRequest().denyAll());
        //http.httpBasic(withDefaults());
        //return http.build();

//        //Default cors Request Currently permits all (switch to .authenticated());) for authentication to occur
//        http.cors(withDefaults()).authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//
//        //Default Form Login On Page
//        http.formLogin(withDefaults());
//
//        //Disables CSRF Tokens
//        http.csrf((csrf) -> csrf.disable());
//
//        //Default browser implement
//        http.httpBasic(withDefaults());
//        return http.build();
        http = http.cors(withDefaults()).csrf((csrf)-> csrf.disable());
        http = http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // .and());

        http = http.exceptionHandling((exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        })));

        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


}
