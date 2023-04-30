package com.example.livescore.web.topic;

import lombok.Data;

@Data
public class CreateTopicDTO {

    private String topicName;
    private Long tournamentId;
    private String registrationToken;
}
