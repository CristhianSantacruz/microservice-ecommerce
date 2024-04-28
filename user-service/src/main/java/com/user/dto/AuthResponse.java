package com.user.dto;

public class AuthResponse {

    public String tokenGenerated;

    public AuthResponse(String tokenGenerated) {
        this.tokenGenerated = tokenGenerated;
    }
}