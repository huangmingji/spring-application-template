package com.stargazer.springapplicationtemplate.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.springapplicationtemplate.controllers.models.AuthResponse;
import com.stargazer.springapplicationtemplate.controllers.models.LoginRequest;
import com.stargazer.springapplicationtemplate.controllers.models.RegisterRequest;
import com.stargazer.springapplicationtemplate.services.interfaces.IUserServices;
import com.stargazer.springapplicationtemplate.services.models.users.UserModel;
import com.stargazer.springapplicationtemplate.utils.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "认证管理")
@RestController
@RequestMapping(value = "auth")
public class AuthController {

    private final IUserServices userServices;
    private final JwtUtils jwtUtils;

    public AuthController(IUserServices userServices, JwtUtils jwtUtils) {
        this.userServices = userServices;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "用户注册")
    @PostMapping("register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount(request.getAccount());
        loginRequest.setPassword(request.getPassword());

        UserModel user = userServices.register(loginRequest);
        String token = jwtUtils.generateToken(user.getId(), user.getAccount());

        return new AuthResponse(token, user.getId(), user.getAccount(), user.getNickName());
    }

    @Operation(summary = "用户登录")
    @PostMapping("login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        UserModel user = userServices.login(request);
        String token = jwtUtils.generateToken(user.getId(), user.getAccount());

        return new AuthResponse(token, user.getId(), user.getAccount(), user.getNickName());
    }
}