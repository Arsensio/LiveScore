package com.example.livescore.web.events;

import com.example.livescore.web.assists.AssistDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    private Long eventId;
    private Integer minute;
    private String gameScore;
    private String eventName;
    private String playerName;
    private Long playerId;
    private Long teamId;
    private String teamName;
    private AssistDTO assist;
}
