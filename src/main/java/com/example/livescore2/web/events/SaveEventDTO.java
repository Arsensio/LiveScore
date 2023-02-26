package com.example.livescore2.web.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveEventDTO {
    private Long eventId;
    private Long protocolId;
    private Long eventEnumId;
    private Long playerId;
    private Integer minute;
}
