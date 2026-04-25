package com.stargazer.springapplicationtemplate.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.stargazer.springapplicationtemplate.domain.Permission;
import com.stargazer.springapplicationtemplate.domain.Role;
import com.stargazer.springapplicationtemplate.repositories.IPermissionRepository;
import com.stargazer.springapplicationtemplate.repositories.IRoleRepository;

@Service
public class PermissionService {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

    public PermissionService(IRoleRepository roleRepository, IPermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<GrantedAuthority> getAuthoritiesByUserId(long userId) {
        List<Role> roles = roleRepository.findByUserId(userId);
        List<Permission> permissions = permissionRepository.findByUserId(userId);

        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        authorities.addAll(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet()));

        return authorities.stream().toList();
    }

    public List<Permission> getPermissionsByUserId(long userId) {
        return permissionRepository.findByUserId(userId);
    }

    public List<Role> getRolesByUserId(long userId) {
        return roleRepository.findByUserId(userId);
    }
}