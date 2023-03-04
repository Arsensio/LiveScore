package com.example.livescore.web.games;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long gameId;
    private Long groupId;
    private String gameScore;
    private boolean isPlayed;
}
