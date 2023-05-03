package com.example.livescore.web.teams;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveTeamDTO {

    private String teamName;
    private String teamLogo;
    private long tournamentId;
}
