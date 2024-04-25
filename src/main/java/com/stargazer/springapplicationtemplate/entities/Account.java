package com.stargazer.springapplicationtemplate.entities;

import java.io.Serializable;

import com.stargazer.springapplicationtemplate.utils.EntityBase;

public class Account extends EntityBase implements Serializable {

    public Account(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private long id;

    private String username;

    private String password;

    private String secretKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}