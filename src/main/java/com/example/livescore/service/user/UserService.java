package com.example.livescore.service.user;

import com.example.livescore.models.UserEntity;
import com.example.livescore.web.user.User;

public interface UserService {

    User changePassword(String username, String userPassword);

    User changeUsername(String oldUsername, String newUsername);

    UserEntity findByUsername(String username);
}
