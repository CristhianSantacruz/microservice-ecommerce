package com.user.utils;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService {

    public String generateToken(String username,String rol){
        return  Jwt.issuer("user-service")
                .subject(username)
                .groups(rol)
                .expiresAt(System.currentTimeMillis()+3600)
                .sign();
    }
}
