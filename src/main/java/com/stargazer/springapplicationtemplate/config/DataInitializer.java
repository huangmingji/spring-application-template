package com.stargazer.springapplicationtemplate.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.stargazer.springapplicationtemplate.domain.Permission;
import com.stargazer.springapplicationtemplate.domain.Role;
import com.stargazer.springapplicationtemplate.repositories.IPermissionRepository;
import com.stargazer.springapplicationtemplate.repositories.IRoleRepository;
import com.stargazer.springapplicationtemplate.utils.SnowflakeIdGenerator;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    private static final List<String> PERMISSION_NAMES = List.of(
        "user:create",
        "user:read",
        "user:read:list",
        "user:update",
        "user:update:avatar",
        "user:delete",
        "auth:login",
        "auth:register"
    );

    public DataInitializer(IRoleRepository roleRepository, IPermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        initializePermissions();
        initializeRoles();
        assignPermissionsToAdminRole();
    }

    private void initializePermissions() {
        for (String name : PERMISSION_NAMES) {
            if (!permissionRepository.existsByName(name)) {
                Permission permission = new Permission();
                permission.setId(SnowflakeIdGenerator.getInstance().nextId());
                permission.setName(name);
                permission.setDescription("Permission for " + name);
                permissionRepository.insert(permission);
            }
        }
    }

    private void initializeRoles() {
        if (!roleRepository.existsByName(ADMIN_ROLE)) {
            Role adminRole = new Role();
            adminRole.setId(SnowflakeIdGenerator.getInstance().nextId());
            adminRole.setName(ADMIN_ROLE);
            adminRole.setDescription("Administrator role with full access");
            roleRepository.insert(adminRole);
        }

        if (!roleRepository.existsByName(USER_ROLE)) {
            Role userRole = new Role();
            userRole.setId(SnowflakeIdGenerator.getInstance().nextId());
            userRole.setName(USER_ROLE);
            userRole.setDescription("Regular user role with limited access");
            roleRepository.insert(userRole);
        }
    }

    private void assignPermissionsToAdminRole() {
        Role adminRole = roleRepository.findByName(ADMIN_ROLE);
        if (adminRole == null) {
            return;
        }

        List<Permission> allPermissions = permissionRepository.findAll();
        Set<Permission> permissions = new HashSet<>(allPermissions);
        adminRole.setPermissions(permissions);
    }
}