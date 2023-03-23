package com.example.livescore.web.protocols;

import com.example.livescore.enums.GameState;
import com.example.livescore.web.events.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolDTO {

    private Long protocolId;
    private Long gameId;
    private String team1;
    private String team2;
    private LocalDateTime dateAndTime;
    private String gameScore;
    private List<EventDTO> events;
    private GameState gameState;
}
