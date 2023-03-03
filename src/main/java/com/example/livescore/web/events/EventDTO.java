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

    private Long eventId;
    private Long protocolId;
    private String eventName;
    private String playerName;
    private Integer minute;
}
