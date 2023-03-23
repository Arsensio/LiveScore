package com.example.livescore.web.games;

import com.example.livescore.enums.GameState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long gameId;
    private Long groupId;
    private String team1Name;
    private String team2Name;
    private String team1Logo;
    private String team2Logo;
    private String gameScore;
    private GameState gameState;
    private Long protocolId;
    private LocalDateTime gameDateTime;
}
