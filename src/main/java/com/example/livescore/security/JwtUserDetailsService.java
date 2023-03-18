package com.example.livescore.security;

import com.example.livescore.models.UserEntity;
import com.example.livescore.security.jwt.JwtUserFactory;
import com.example.livescore.service.user.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final DefaultUserService userService;

    @Autowired
    public JwtUserDetailsService(DefaultUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found!");
        }
        return JwtUserFactory.create(user);
    }
}
