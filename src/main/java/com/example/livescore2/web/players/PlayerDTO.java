package com.example.livescore2.web.players;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Long playerId;

    private String teamName;

    private String name;

    private String surname;

    private Integer playerNumber;

    private String role;

}
