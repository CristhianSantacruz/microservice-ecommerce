package com.user.dto;

public class AuthRequest {

    public String email;
    public String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
