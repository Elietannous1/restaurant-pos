package com.example.restaurant_pos.model.request;


public class JwtDTO {
    private String token;
    private String refreshToken;

    public JwtDTO(String token, String refreshToken) {
        setToken(token);
        setRefreshToken(refreshToken);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
