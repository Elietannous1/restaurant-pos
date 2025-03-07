package com.example.restaurant_pos.frontend.controller.utils;

public class TokenManager {

    // Static variable to hold the singleton instance
    private static TokenManager instance;

    // Token and Refresh Token for the current session
    private String token;
    private String refreshToken;

    // Private constructor to prevent instantiation
    private TokenManager() {}

    // Public method to get the single instance of TokenManager
    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    // Setters and Getters for token and refresh token
    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    // Clear tokens (e.g., when logging out)
    public void clearTokens() {
        this.token = null;
        this.refreshToken = null;
    }
}
