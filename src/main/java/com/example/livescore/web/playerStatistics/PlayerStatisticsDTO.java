package com.example.livescore.web.playerStatistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatisticsDTO {

    private Long id;

    private Integer matchPlayed;

    private Integer goals;

    private Integer assists;

    private Integer yellowCard;

    private Integer redCard;
}
