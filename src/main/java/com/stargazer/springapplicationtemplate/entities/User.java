package com.stargazer.springapplicationtemplate.entities;

import java.io.Serializable;

import com.stargazer.springapplicationtemplate.utils.EntityBase;

public class User extends EntityBase implements Serializable {

    public User(long id, String name, String nickName, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    private long id;

    private String name;

    private String nickName;

    private String avatar;

    private String email;

    private boolean emailVerified;

    private String phoneNumber;

    private boolean phoneNumbertVerified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumbertVerified() {
        return phoneNumbertVerified;
    }

    public void setPhoneNumbertVerified(boolean phoneNumbertVerified) {
        this.phoneNumbertVerified = phoneNumbertVerified;
    }
}
