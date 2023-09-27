package com.example.meetingrooms.DTO;

public class AuthCredentialsRequest {
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }
    public void setUsername(){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
