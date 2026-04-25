package com.stargazer.springapplicationtemplate.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.springapplicationtemplate.controllers.models.PageResponse;
import com.stargazer.springapplicationtemplate.services.interfaces.IUserServices;
import com.stargazer.springapplicationtemplate.services.models.users.CreateOrUpdateUserModel;
import com.stargazer.springapplicationtemplate.services.models.users.UserModel;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataAlreadyExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataNotExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterEmptyException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterInvalidException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "用户管理")
@RestController
@RequestMapping(value = "user")
public class UserController {

    private final IUserServices userServices;

    public UserController(IUserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(summary = "添加用户")
    @PostMapping
    @PreAuthorize("hasAuthority('user:create') or hasRole('ADMIN')")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid CreateOrUpdateUserModel model) {
        try {
            return ResponseEntity.ok(userServices.createUser(model));
        } catch (ParameterEmptyException | ParameterInvalidException e) {
            return ResponseEntity.badRequest().build();
        } catch (DataAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "更新用户")
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('user:update') or hasRole('ADMIN')")
    public ResponseEntity<UserModel> update(
            @PathVariable long id,
            @RequestBody @Valid CreateOrUpdateUserModel model) {
        try {
            return ResponseEntity.ok(userServices.updateUser(id, model));
        } catch (DataNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (ParameterEmptyException | ParameterInvalidException e) {
            return ResponseEntity.badRequest().build();
        } catch (DataAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "获取用户")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user:read') or hasRole('ADMIN')")
    public ResponseEntity<UserModel> get(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userServices.findUserById(id));
        } catch (DataNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user:delete') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            userServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (DataNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('user:read:list') or hasRole('ADMIN')")
    public ResponseEntity<PageResponse<UserModel>> getUsersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<UserModel> users = userServices.getUsersByPage(page, size);
        PageResponse<UserModel> pageResponse = new PageResponse<>(page, size, users.size(), users);
        return ResponseEntity.ok(pageResponse);
    }
}