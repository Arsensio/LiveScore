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
    private boolean isPlayed;
    private String gameScore;

}
