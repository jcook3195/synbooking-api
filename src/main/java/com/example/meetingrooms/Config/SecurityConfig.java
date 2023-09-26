package com.example.meetingrooms.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.csrf.CsrfToken;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@CrossOrigin("http://localhost:3000")
public class SecurityConfig {
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

        //Default cors Request Currently permits all (switch to .authenticated());) for authentication to occur
        http.cors(withDefaults()).authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

//        //Default Form Login On Page
//        http.formLogin(withDefaults());
//
//        //Disables CSRF Tokens
//        http.csrf((csrf) -> csrf.disable());

        //Default browser implement
        http.httpBasic(withDefaults());
        return http.build();

    }

}
