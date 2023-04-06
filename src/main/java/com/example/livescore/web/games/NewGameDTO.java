package com.example.livescore.web.games;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewGameDTO {

    private String tournamentName;
    private String tournamentLogo;
    private String groupName;
    private List<GameDTO> games;
}
