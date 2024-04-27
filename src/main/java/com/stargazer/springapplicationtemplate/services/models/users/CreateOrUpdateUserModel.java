package com.stargazer.springapplicationtemplate.services.models.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateOrUpdateUserModel {

    @NotNull(message = "account is required")
    @NotEmpty(message = "account is required")
    public String account;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    public String password;

    @NotNull(message = "nickName is required")
    @NotEmpty(message = "nickName is required")
    public String nickName;

    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    public String email;

    @NotNull(message = "phoneNumber is required")
    @NotEmpty(message = "phoneNumber is required")
    public String phoneNumber;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
