package com.example.restaurant_pos.model;

import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;


    private String token;
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Token(){}

    public Token(String token, User user){
        setToken(token);
        setUser(user);
    }

    private void setUser(User user) { this.user = user; }

    public int getTokenId() { return tokenId; }

    public void setTokenId(int tokenId) { this.tokenId = tokenId; }
    public String getToken(){ return this.token; }

    public void setToken(String token){ this.token = token; }
    public User getUser() { return this.user; }

    public boolean isExpired(){ return this.expired; }

    public boolean isRevoked(){ return this.revoked; }

    public void setExpired(boolean expired){ this.expired = expired; }

    public void setRevoked(boolean revoked){ this.revoked = revoked;}
}
