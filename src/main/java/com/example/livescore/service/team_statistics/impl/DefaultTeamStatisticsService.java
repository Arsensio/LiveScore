package com.example.livescore.service.team_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.StatisticDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultTeamStatisticsService extends AbstractFootballService<TeamStatisticsEntity, TeamStatisticsDTO,
        SaveTeamStatisticsDTO, TeamStatisticsEntityPK, TeamStatisticsRepository>
        implements TeamStatisticsService {

    public DefaultTeamStatisticsService(TeamStatisticsRepository repository, GroupRepository groupRepository) {
        super(repository);
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId) {
        return repository.findAllByGroupIdOrderByGoalCount(groupId)
                .stream()
                .map(team -> team.toDistinctStatisticsDTO(EventNames.GOAL.getEventName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId) {
        return repository.findAllByGroupIdOrderByWinCount(groupId)
                .stream()
                .map(TeamStatisticsEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByRedCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.RED_CARD))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByYellowCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.YELLOW_CARD))
                .collect(Collectors.toList());
    }

}
