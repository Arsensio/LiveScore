package com.example.livescore.web.players;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinPlayerDto {

    private Long playerId;
    private String name;
    private String surname;
    private Integer playerNumber;
}
