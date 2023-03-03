package com.example.livescore.service.player_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.repository.PlayerStatisticsRepository;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlayerStatisticsService
        extends AbstractFootballService<PlayerStatisticsEntity, PlayerStatisticsDTO,
        SavePlayerStatisticsDTO, PlayerStatisticsEntityPK, PlayerStatisticsRepository>
        implements PlayerStatisticsService {

    public DefaultPlayerStatisticsService(PlayerStatisticsRepository repository) {
        super(repository);
    }
}
