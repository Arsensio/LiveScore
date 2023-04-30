package com.example.livescore.web.tournaments;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCupTournamentDTO {

    private String tournamentName;
    private String tournamentType;
    private String tournamentLogo;
    private String location;
    private Integer teamsNum;
    private boolean isPlayOf;
}
