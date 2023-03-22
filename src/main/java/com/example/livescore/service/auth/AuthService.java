package com.example.livescore.service.auth;

import com.example.livescore.web.auth.AuthRequest;
import com.example.livescore.web.auth.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);
}
