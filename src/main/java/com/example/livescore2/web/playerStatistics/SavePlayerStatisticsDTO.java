package com.example.livescore2.web.playerStatistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavePlayerStatisticsDTO {

    private Long matchPlayed;

    private Long goals;

    private Long assists;

    private Long yellowCard;

    private Long redCard;
}
