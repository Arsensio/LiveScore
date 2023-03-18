package com.example.livescore.web.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String username;
    private String userPassword;
    private String userRole;
    private Boolean blockFlag;
}
