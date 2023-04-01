package com.example.livescore.service.user.impl;

import com.example.livescore.models.UserEntity;
import com.example.livescore.repository.UserRepository;
import com.example.livescore.service.user.UserService;
import com.example.livescore.web.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User changePassword(String username, String password) {
        userRepository.updateUserPassword(username, passwordEncoder.encode(password));
        return userRepository.findUserEntityByUsername(username).toDto();
    }

    @Transactional
    @Override
    public User changeUsername(String oldUsername, String newUsername) {
        userRepository.updateUsername(oldUsername, newUsername);
        return userRepository.findUserEntityByUsername(newUsername).toDto();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }
}
