package com.example.livescore.service.auth.impl;

import com.example.livescore.security.JwtService;
import com.example.livescore.service.auth.AuthService;
import com.example.livescore.web.auth.AuthRequest;
import com.example.livescore.web.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(authRequest.getUsername());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
