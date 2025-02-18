package com.example.restaurant_pos.model.request;

import com.example.restaurant_pos.model.Token;

public class JwtDTO {
    private String token;

    public JwtDTO(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
