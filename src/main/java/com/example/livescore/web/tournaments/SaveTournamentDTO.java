package com.example.livescore.web.tournaments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTournamentDTO {

    private String tournamentName;
    private String tournamentType;
    private String tournamentLogo;
    private String location;
    private Integer teamsNum;
}
