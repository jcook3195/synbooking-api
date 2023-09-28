package com.example.meetingrooms.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //OVERRIDE METHODS FOR IMPLEMENTATION
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override //Required method overwrite but unused
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //Required method overwrite but unused
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //Required method overwrite but unused
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //Required method overwrite but unused
    public boolean isEnabled() {
        return true;
    }

    @Override //Required method overwrite but unused (May implement later)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
