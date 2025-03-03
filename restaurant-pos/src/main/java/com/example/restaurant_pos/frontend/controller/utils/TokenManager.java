package com.example.restaurant_pos.frontend.controller.utils;

public class TokenManager {

    private String token;
    private String refreshToken;

    public TokenManager(String token, String refreshToken) {
        setToken(token);
        setRefreshToken(refreshToken);
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
