package com.example.livescore2.web.playerStatistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InitPlayerStatisticDTO {
    private Long playerId;
    private Long groupId;
}
