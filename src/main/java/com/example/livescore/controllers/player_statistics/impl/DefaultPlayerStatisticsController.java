package com.example.livescore.controllers.player_statistics.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.player_statistics.PlayerStatisticsController;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player_statistics")
public class DefaultPlayerStatisticsController
        extends AbstractFootballController<PlayerStatisticsService, PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK> implements PlayerStatisticsController {

    public DefaultPlayerStatisticsController(PlayerStatisticsService service) {
        super(service);
    }
}
