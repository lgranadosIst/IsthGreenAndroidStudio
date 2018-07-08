package com.isthmusit.isthgreen.isthgreenapp.entity;

public class UserSession {

    private String Token;

    private String UserId;

    public UserSession(String token, String userId) {
        Token = token;
        UserId = userId;
    }

    public UserSession() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}
