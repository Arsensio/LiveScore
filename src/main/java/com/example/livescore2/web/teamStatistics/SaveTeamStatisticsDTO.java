package com.example.livescore2.web.teamStatistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTeamStatisticsDTO {

    private Long team_id;
    private Long group;
    private Integer gamePlayed;
    private Integer winCount;
    private Integer loseCount;
    private Integer drawCount;
    private Integer goalCount;
    private Integer goalMissed;
    private Integer points;
}
