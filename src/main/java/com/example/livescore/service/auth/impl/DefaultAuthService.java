package com.example.livescore.service.auth.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.livescore.models.UserEntity;
import com.example.livescore.repository.UserRepository;
import com.example.livescore.security.JwtService;
import com.example.livescore.service.auth.AuthService;
import com.example.livescore.web.auth.AuthRequest;
import com.example.livescore.web.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserEntity userEntity = getUserEntity(authRequest.getUsername());
        System.out.println(userEntity.getUserId());

        String jwtToken = jwtService.generateToken(authRequest.getUsername(),userEntity.getUserId());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    private UserEntity getUserEntity(String username) {
        Optional<UserEntity> userEntityByUsername = userRepository.findUserEntityByUsername(username);
        if (userEntityByUsername.isEmpty()){
            throw ResourceNotFoundException.build(username, "UserEntity");
        }
        return userEntityByUsername.get();
    }
}
