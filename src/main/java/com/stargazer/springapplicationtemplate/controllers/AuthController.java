package com.stargazer.springapplicationtemplate.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;
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
import com.stargazer.springapplicationtemplate.utils.exceptions.DataAlreadyExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataNotExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterEmptyException;
import com.stargazer.springapplicationtemplate.utils.exceptions.VerifyPasswordException;

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
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            if (request.getAccount() == null || request.getAccount().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setAccount(request.getAccount());
            loginRequest.setPassword(request.getPassword());

            UserModel user = userServices.register(loginRequest);
            String token = jwtUtils.generateToken(user.getId(), user.getAccount());

            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getAccount(), user.getNickName()));
        } catch (DataAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "用户登录")
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            if (request.getAccount() == null || request.getAccount().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            UserModel user = userServices.login(request);
            String token = jwtUtils.generateToken(user.getId(), user.getAccount());

            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getAccount(), user.getNickName()));
        } catch (VerifyPasswordException e) {
            return ResponseEntity.status(401).build();
        } catch (DataNotExistsException e) {
            return ResponseEntity.status(401).build();
        } catch (ParameterEmptyException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return ResponseEntity.status(500).build();
        }
    }
}