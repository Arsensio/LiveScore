package com.example.livescore.web.teams;

import com.example.livescore.web.players.MinPlayerDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamWithPlayersDto {

    private Long teamId;
    private String teamName;
    private String teamLogo;
    private List<MinPlayerDto> players;
}

