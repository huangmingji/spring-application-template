package com.stargazer.springapplicationtemplate.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stargazer.springapplicationtemplate.domain.User;
import com.stargazer.springapplicationtemplate.domain.UserPrincipal;
import com.stargazer.springapplicationtemplate.repositories.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final PermissionService permissionService;

    public CustomUserDetailsService(IUserRepository userRepository, PermissionService permissionService) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + account);
        }
        List<GrantedAuthority> authorities = permissionService.getAuthoritiesByUserId(user.getId());
        return new UserPrincipal(user, authorities);
    }

    public UserDetails loadUserById(long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        List<GrantedAuthority> authorities = permissionService.getAuthoritiesByUserId(id);
        return new UserPrincipal(user, authorities);
    }
}