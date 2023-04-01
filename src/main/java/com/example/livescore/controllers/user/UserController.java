package com.example.livescore.controllers.user;


import com.example.livescore.service.user.impl.DefaultUserService;
import com.example.livescore.web.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final DefaultUserService userService;

    @PutMapping("/putOwn/updateUsername")
    public User changeUsername(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "username") String username) {
        return userService.changeUsername(userDetails.getUsername(), username);
    }

    @PutMapping("/putOwn/updatePassword")
    public User changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "password") String password) {
        return userService.changePassword(userDetails.getUsername(), password);
    }
}
