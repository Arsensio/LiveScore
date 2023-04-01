package com.example.livescore.controllers.auth;

import com.example.livescore.web.auth.AuthRequest;
import com.example.livescore.web.auth.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}
