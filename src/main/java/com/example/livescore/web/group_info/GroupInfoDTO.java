package com.example.livescore.web.group_info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoDTO {

    private Integer position;
    private String teamName;
    private String teamLogo;
    private Integer gamePlayed;
    private Integer winCount;
    private Integer drawCount;
    private Integer loseCount;
    private Integer goalCount;
    private Integer goalMissed;
    private Integer points;
    private boolean isLive;

    public GroupInfoDTO(String teamName, String teamLogo, Integer gamePlayed, Integer winCount, Integer drawCount, Integer loseCount, Integer goalCount, Integer goalMissed, Integer points) {
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.gamePlayed = gamePlayed;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.loseCount = loseCount;
        this.goalCount = goalCount;
        this.goalMissed = goalMissed;
        this.points = points;
    }

    public GroupInfoDTO(String teamName, String teamLogo, Integer gamePlayed, Integer winCount, Integer drawCount, Integer loseCount, Integer goalCount, Integer goalMissed, Integer points, boolean isLive) {
        this.teamName = teamName;
        this.teamLogo = teamLogo;
        this.gamePlayed = gamePlayed;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.loseCount = loseCount;
        this.goalCount = goalCount;
        this.goalMissed = goalMissed;
        this.points = points;
        this.isLive = isLive;
    }
}
