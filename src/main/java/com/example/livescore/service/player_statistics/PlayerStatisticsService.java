package com.example.livescore.service.player_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;

public interface PlayerStatisticsService
        extends FootballService<PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK> {
}
