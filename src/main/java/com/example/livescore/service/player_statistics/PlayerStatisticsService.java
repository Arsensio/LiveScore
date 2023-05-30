package com.example.livescore.service.player_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.*;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;

import java.util.List;

public interface PlayerStatisticsService extends FootballService<PlayerStatisticsEntity, PlayerStatisticsDTO, SavePlayerStatisticsDTO,
        PlayerStatisticsEntityPK> {

    List<DistinctPlayerStatisticsDTO> findAllByGoals(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByYellowCard(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByRedCard(long groupId);

    List<DistinctPlayerStatisticsDTO> findAllByAssists(long groupId);

    PlayerStatisticsEntity saveAndFlush(PlayerStatisticsEntity player);

    PlayerStatisticsEntity save(PlayerEntity player, TournamentEntity tournament);

    void incrementGamePlayed(PlayerStatisticsEntityPK id);
}
