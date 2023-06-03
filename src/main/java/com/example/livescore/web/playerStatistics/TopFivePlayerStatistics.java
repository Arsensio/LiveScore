package com.example.livescore.web.playerStatistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopFivePlayerStatistics {
    private String statName;
    private List<DistinctPlayerStatisticsDTO> statistics;
}
