package com.example.livescore.service.team_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.*;
import com.example.livescore.repository.ProtocolRepository;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.StatisticDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTeamStatisticsService
        extends AbstractFootballService<TeamStatisticsEntity, TeamStatisticsDTO,
        SaveTeamStatisticsDTO, TeamStatisticsEntityPK, TeamStatisticsRepository>
        implements TeamStatisticsService {

    private final ProtocolRepository protocolRepository;


    public DefaultTeamStatisticsService(TeamStatisticsRepository repository, ProtocolRepository protocolRepository) {
        super(repository);
        this.protocolRepository = protocolRepository;
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId) {
        return repository.findAllByGroupIdOrderByGoalCount(groupId)
                .stream()
                .map(team -> team.toDistinctStatisticsDTO(EventNames.GOAL.getEventName()))
                .toList();
    }

    @Override
    public List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId) {
        List<TeamStatisticsEntity> allByGroupIdOrderByWinCount = repository.findAllByGroupIdOrderByWinCount(groupId);
        List<ProtocolEntity> allByGameStateStarted = protocolRepository.findAllByGameStateStarted();
        List<TeamStatisticsDTO> orderedByPointList = new ArrayList<>();

        for (TeamStatisticsEntity teamStatistics : allByGroupIdOrderByWinCount) {
            for (ProtocolEntity protocolEntity : allByGameStateStarted) {
                int team1Score = protocolEntity.getTeam1Score();
                int team2Score = protocolEntity.getTeam2Score();

                if (teamStatistics.getId().getTeam() == protocolEntity.getTeam1()) {
                    updatePointsAndStatistic(team1Score, team2Score, teamStatistics);
                } else if (teamStatistics.getId().getTeam() == protocolEntity.getTeam2()) {
                    updatePointsAndStatistic(team2Score, team1Score, teamStatistics);
                }
            }
            orderedByPointList.add(teamStatistics.toDTO());
        }

        orderedByPointList.sort((o1, o2) ->
                o2.getPoints().compareTo(o1.getPoints())
        );

        return orderedByPointList;
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByRedCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.RED_CARD))
                .toList();
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByYellowCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.YELLOW_CARD))
                .toList();
    }

    @Override
    public TeamStatisticsDTO save(GroupEntity group, TeamEntity team) {
        TeamStatisticsEntityPK pk = new TeamStatisticsEntityPK(group, team);
        TeamStatisticsEntity saved = repository.save(new TeamStatisticsEntity(
                pk,
                0,
                0,
                0,
                0,
                0,
                0,
                0
        ));

        return saved.toDTO();
    }

    @Override
    public List<TeamDTO> findAllTeamByGroupId(long groupId) {
        return repository.findAllTeamByGroupId(groupId);
    }

    @Override
    public void incrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK) {
        repository.incrementGoalCount(teamStatisticsEntityPK);
    }

    @Override
    public void incrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK) {
        repository.incrementGoalMissedCount(teamStatisticsEntityPK);
    }

    @Override
    public void decrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK) {
        repository.decrementGoalCount(teamStatisticsEntityPK);
    }

    @Override
    public void decrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK) {
        repository.decrementGoalMissedCount(teamStatisticsEntityPK);
    }

    @Override
    public void incrementGameCount(TeamStatisticsEntityPK teamStatisticsEntityPK) {
        repository.incrementGameCount(teamStatisticsEntityPK);
    }

    private void updatePointsAndStatistic(int foundTeam, int rivalTeam, TeamStatisticsEntity teamStatistics) {
        if (foundTeam > rivalTeam) {
            teamStatistics.setWinCount(teamStatistics.getWinCount() + 1);
            teamStatistics.setPoints(teamStatistics.getPoints() + 3);
        } else if (foundTeam == rivalTeam) {
            teamStatistics.setDrawCount(teamStatistics.getDrawCount() + 1);
            teamStatistics.setPoints(teamStatistics.getPoints() + 1);
        } else {
            teamStatistics.setLoseCount(teamStatistics.getLoseCount() + 1);
        }
    }
}
