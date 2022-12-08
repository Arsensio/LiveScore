package com.example.livescore.web.tournaments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {

    private Long tournamentId;
    private String tournamentName;
    private String tournamentType;

}
