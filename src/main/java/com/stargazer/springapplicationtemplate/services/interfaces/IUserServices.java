package com.stargazer.springapplicationtemplate.services.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.stargazer.springapplicationtemplate.services.models.users.CreateAccountModel;
import com.stargazer.springapplicationtemplate.services.models.users.CreateOrUpdateUserModel;
import com.stargazer.springapplicationtemplate.services.models.users.UpdateAvatarModel;
import com.stargazer.springapplicationtemplate.services.models.users.UserModel;
import com.stargazer.springapplicationtemplate.services.models.users.VerifyPasswordModel;

public interface IUserServices {
    
    UserModel createAccount(CreateAccountModel model) throws NoSuchAlgorithmException, InvalidKeySpecException;

    UserModel createUser(CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException;

    UserModel updateUser(long id, CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException;

    UserModel updateAvatar(UpdateAvatarModel model);

    UserModel findUserById(long id);

    UserModel verifyPassword(VerifyPasswordModel model) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void delete(long id);

    List<UserModel> getUsersByPage(int page, int size);

}
