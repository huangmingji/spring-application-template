package com.stargazer.springapplicationtemplate.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.springapplicationtemplate.services.interfaces.IUserServices;
import com.stargazer.springapplicationtemplate.services.models.users.CreateOrUpdateUserModel;
import com.stargazer.springapplicationtemplate.services.models.users.UserModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "用户管理")
@RestController
@RequestMapping(value = "user", consumes = "application/json")
public class UserController {

    private IUserServices userServices;
    public UserController(IUserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(summary = "添加用户")
    @PostMapping(value = "")
    public UserModel createUser(@RequestBody @Valid CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userServices.createUser(model);
    }

    @Operation(summary = "更新用户")
    @PutMapping(value = "{id}")
    public UserModel update(@PathVariable long id, @RequestBody @Valid CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userServices.updateUser(id, model);
    }
    
    @Operation(summary = "获取用户")
    @GetMapping(value = "{id}")
    public UserModel get(@PathVariable long id)
    {
        return userServices.findUserById(id);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("{id}")
    public void delete(@PathVariable long id)
    {
        userServices.delete(id);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("list")
    public List<UserModel> getUsersByPage(int page, int size)
    {
        return userServices.getUsersByPage(page, size);
    }
}
