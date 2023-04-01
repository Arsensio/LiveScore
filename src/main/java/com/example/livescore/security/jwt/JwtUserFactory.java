package com.example.livescore.security.jwt;

import com.example.livescore.models.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserEntity user) {
        return new JwtUser(
                user.getUserId(),
                user.getUsername(),
                user.getUserPassword(),
                !user.getBlockFlag(),
                List.of(new SimpleGrantedAuthority(user.getUserRole()))
        );
    }
}
