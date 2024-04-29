package com.stargazer.springapplicationtemplate.domain;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

import com.stargazer.springapplicationtemplate.utils.EntityBase;
import com.stargazer.springapplicationtemplate.utils.PasswordSecurity;
import com.stargazer.springapplicationtemplate.utils.exceptions.VerifyPasswordException;

public class User extends EntityBase implements Serializable {
    
    private long id;

    private String account;

    private String password;

    private String secretKey;

    private String nickName;

    private String avatar = "";

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.secretKey = PasswordSecurity.createSecretKey();
        this.password = PasswordSecurity.createHash(password, this.secretKey);
    }

    public void VerifyPassword(String password) throws VerifyPasswordException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        if (!PasswordSecurity.createHash(password, this.secretKey).equals(this.password))
        {
            throw new VerifyPasswordException(this.id);
        }
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
