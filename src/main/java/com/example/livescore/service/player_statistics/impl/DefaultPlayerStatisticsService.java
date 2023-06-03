package com.example.livescore.service.player_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.PlayerStatisticsRepository;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.web.playerStatistics.DistinctPlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.TopFivePlayerStatistics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.livescore.enums.EventEnum.*;

@Service
public class DefaultPlayerStatisticsService
        extends AbstractFootballService<PlayerStatisticsEntity, PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK, PlayerStatisticsRepository>
        implements PlayerStatisticsService {

    public DefaultPlayerStatisticsService(PlayerStatisticsRepository repository) {
        super(repository);
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByGoals(long groupId) {
        return repository.findAllByGoals(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("GOALS"))
                .toList();
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByYellowCard(long groupId) {
        return repository.findAllByYellowCard(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("YELLOW CARD"))
                .toList();
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByRedCard(long groupId) {
        return repository.findAllByRedCard(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("RED CARD"))
                .toList();
    }

    @Override
    public List<DistinctPlayerStatisticsDTO> findAllByAssists(long groupId) {
        return repository.findAllByAssists(groupId)
                .stream()
                .map(playerStatisticsEntity -> playerStatisticsEntity.distinctDTO("ASSISTS"))
                .toList();
    }

    @Override
    public PlayerStatisticsEntity saveAndFlush(PlayerStatisticsEntity playerStatistics) {
        return repository.saveAndFlush(playerStatistics);
    }

    @Override
    public PlayerStatisticsEntity save(PlayerEntity player, TournamentEntity tournament) {
        PlayerStatisticsEntityPK pk = new PlayerStatisticsEntityPK(tournament, player);
        return repository.save(getDefaultPlayerStatisticsEntity(pk));
    }

    @Override
    public void incrementGamePlayed(PlayerStatisticsEntityPK id) {
        repository.incrementGameCount(id);
    }

    @Override
    public List<TopFivePlayerStatistics> findTopFivePlayerStatistics(long tournament_id) {
        List<TopFivePlayerStatistics> topFivePlayerStatistics = new ArrayList<>();
        topFivePlayerStatistics.add(getDefaultTopFivePlayerStatistics(GOAL, findAllByGoals(tournament_id)));
        topFivePlayerStatistics.add(getDefaultTopFivePlayerStatistics(ASSIST, findAllByAssists(tournament_id)));
        topFivePlayerStatistics.add(getDefaultTopFivePlayerStatistics(RED_CARD, findAllByRedCard(tournament_id)));
        topFivePlayerStatistics.add(getDefaultTopFivePlayerStatistics(YELLOW_CARD, findAllByYellowCard(tournament_id)));

        return topFivePlayerStatistics;
    }

    private TopFivePlayerStatistics getDefaultTopFivePlayerStatistics(EventEnum eventEnum, List<DistinctPlayerStatisticsDTO> list) {
        return new TopFivePlayerStatistics(eventEnum.getEventName(), getTopFiveElement(list));
    }

    private List<DistinctPlayerStatisticsDTO> getTopFiveElement(List<DistinctPlayerStatisticsDTO> list) {
        return list.stream()
                .limit(5)
                .toList();
    }

    private static PlayerStatisticsEntity getDefaultPlayerStatisticsEntity(PlayerStatisticsEntityPK pk) {
        return new PlayerStatisticsEntity(
                pk,
                0L,
                0L,
                0L,
                0L,
                0L
        );
    }
}
