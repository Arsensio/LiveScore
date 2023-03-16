package com.example.livescore.web.events;

import com.example.livescore.web.assists.AssistDTO;
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
    private AssistDTO assist;
    private boolean isPenalty;
}
