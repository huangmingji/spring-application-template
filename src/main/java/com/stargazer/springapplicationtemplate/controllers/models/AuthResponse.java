package com.stargazer.springapplicationtemplate.controllers.models;

public class AuthResponse {

    private String token;

    private long userId;

    private String account;

    private String nickName;

    public AuthResponse(String token, long userId, String account, String nickName) {
        this.token = token;
        this.userId = userId;
        this.account = account;
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}