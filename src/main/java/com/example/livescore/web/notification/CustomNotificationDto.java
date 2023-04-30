package com.example.livescore.web.notification;

import lombok.Data;

import java.util.List;

@Data
public class CustomNotificationDto {

    private String title;
    private String body;
    private String image = "https://cdn-icons-png.flaticon.com/512/53/53283.png";
    private List<String> registrationTokens;
}
