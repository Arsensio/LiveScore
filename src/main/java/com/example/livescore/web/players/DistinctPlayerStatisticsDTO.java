package com.example.livescore.web.players;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistinctPlayerStatisticsDTO {

    private String statName;
//    private String groupName;
    private String teamName;
    private String teamLogo;
    private String playerName;
    private Long total;
    private String perGame;

    public DistinctPlayerStatisticsDTO(String statName, String teamName, String teamLogo, String playerName) {
        this.statName = statName;
//        this.groupName = groupName;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.playerName = playerName;
    }
}
