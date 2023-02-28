package com.example.livescore.web.teamStatistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatisticsDTO {

    private String groupName;
    private String teamName;
    private Integer gamePlayed;
    private Integer winCount;
    private Integer drawCount;
    private Integer loseCount;
    private Integer goalCount;
    private Integer goalMissed;
    private Integer points;
}
