package com.example.livescore.web.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveGoalEventDTO {

    private Long protocolId;
    private Long playerId;
    private Integer minute;
    private Boolean isPenalty;
    private Long assistId;
}
