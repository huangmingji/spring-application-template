package com.stargazer.springapplicationtemplate.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.springapplicationtemplate.interfaces.dtos.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "用户管理")
@RestController
@RequestMapping(value = "user")
public class UserController {

    // 模拟数据库存储的用户
    private static Map<Long, UserDto> userMap;

    static {
        userMap = new ConcurrentHashMap<>();
        UserDto user = new UserDto();
        userMap.put(user.getId(), user);
    }

    @Operation(summary = "列表查询")
    @GetMapping(value = "")
    public List<UserDto> list() {
        return new ArrayList<>(userMap.values());
    }

    @Operation(summary = "获取用户详细信息", description = "根据id获取用户详细信息")
    @GetMapping(value = "{id}")
    public UserDto detail(@PathVariable Long id) {
        return userMap.get(id);
    }
    
}
