package com.example.livescore.web.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private String eventName;
    private String playerName;
    private Integer minute;
    private Long teamId;
    private String teamName;
    private String gameScore;
}
