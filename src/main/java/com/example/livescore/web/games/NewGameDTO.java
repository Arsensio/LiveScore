package com.example.livescore.web.games;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewGameDTO {

    private Long tournamentId;
    private String tournamentName;
    private String tournamentLogo;
    private String tournamentType;
    private String tournamentLocation;
    private String tournamentStatus;
    private String groupName;
    private Long groupId;
    private List<GameDTO> games;
}
