package com.example.livescore.web.teamStatistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistinctStatisticsDTO {

    private String statName;
    private String groupName;
    private String teamName;
    private Integer total;
    private String perGame;
}
