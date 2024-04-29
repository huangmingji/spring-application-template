package com.stargazer.springapplicationtemplate.services.models.users;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserModel implements Serializable {

    private long id;

    private String account;

    private String nickName;

    private String avatar;

    private String email;

    private boolean emailVerified;

    private String phoneNumber;

    private boolean phoneNumbertVerified;

    private boolean enabled;

    private LocalDateTime lockStartTime;

    private LocalDateTime lockEndTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLockStartTime() {
        return lockStartTime;
    }

    public void setLockStartTime(LocalDateTime lockStartTime) {
        this.lockStartTime = lockStartTime;
    }

    public LocalDateTime getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(LocalDateTime lockEndTime) {
        this.lockEndTime = lockEndTime;
    }
}
