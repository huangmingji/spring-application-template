package com.stargazer.springapplicationtemplate.services.models.users;

public class UpdateAvatarModel {

    public long id;

    public String avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
