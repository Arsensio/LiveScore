package com.example.livescore.service.player_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.repository.PlayerStatisticsRepository;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPlayerStatisticsService
        extends AbstractFootballService<PlayerStatisticsEntity, PlayerStatisticsDTO,
        SavePlayerStatisticsDTO, PlayerStatisticsEntityPK, PlayerStatisticsRepository>
        implements PlayerStatisticsService {

    public DefaultPlayerStatisticsService(PlayerStatisticsRepository repository) {
        super(repository);
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByGoals(long groupId) {
        return repository.findAllByGoals(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("GOALS"))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByYellowCard(long groupId) {
        return repository.findAllByYellowCard(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("YELLOW CARD"))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByRedCard(long groupId) {
        return repository.findAllByRedCard(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("RED CARD"))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByAssists(long groupId) {
        return repository.findAllByAssists(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("ASSISTS"))
                .collect(Collectors.toList());
    }
}
