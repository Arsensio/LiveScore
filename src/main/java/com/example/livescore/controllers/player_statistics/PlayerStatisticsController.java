package com.example.livescore.controllers.player_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface PlayerStatisticsController
        extends FootballController<PlayerStatisticsDTO,SavePlayerStatisticsDTO, PlayerStatisticsEntityPK> {
}
