package com.example.livescore.service.player_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;

import java.util.List;

public interface PlayerStatisticsService
        extends FootballService<PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK> {
    List<DistinctPlayerStatisticsDTO> findAllByGoals(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByYellowCard(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByRedCard(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByAssists(long groupId);

    PlayerStatisticsEntity findEntityById(PlayerStatisticsEntityPK id);

    PlayerStatisticsEntity saveAndFlush(PlayerStatisticsEntity player);

    void incrementGamePlayed(PlayerStatisticsEntityPK id);
}
