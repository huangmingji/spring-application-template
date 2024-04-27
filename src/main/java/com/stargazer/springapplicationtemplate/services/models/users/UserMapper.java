package com.stargazer.springapplicationtemplate.services.models.users;

import java.util.ArrayList;
import java.util.List;

import com.stargazer.springapplicationtemplate.domain.User;

public class UserMapper {

    public static UserModel toDto(User user) {
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setAccount(user.getAccount());
        model.setNickName(user.getNickName());
        model.setEmail(user.getEmail());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setAvatar(user.getAvatar());
        model.setEnabled(user.isEnabled());
        model.setEmailVerified(user.isEmailVerified());
        model.setPhoneNumbertVerified(user.isPhoneNumbertVerified());
        return model;
    }

    public static List<UserModel> toDto(List<User> users) {
        List<UserModel> models = new ArrayList<UserModel>();
        for (User user : users) {
            models.add(toDto(user));
        }
        return models;
    }
}