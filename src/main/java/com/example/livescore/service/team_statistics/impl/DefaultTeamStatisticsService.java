package com.example.livescore.service.team_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.*;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.protocol.ProtocolService;
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

    private final ProtocolService protocolService;


    public DefaultTeamStatisticsService(TeamStatisticsRepository repository, ProtocolService protocolService) {
        super(repository);
        this.protocolService = protocolService;
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId) {
        return repository.findAllByTournamentIdOrderByGoalCount(groupId)
                .stream()
                .map(team -> team.toDistinctStatisticsDTO(EventNames.GOAL.getEventName()))
                .toList();
    }

    @Override
    public List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId) {
        List<TeamStatisticsEntity> allByTournamentIdOrderByWinCount = repository.findAllByTournamentIdOrderByWinCount(groupId);
        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();
        List<TeamStatisticsDTO> orderedByPointList = new ArrayList<>();

        for (TeamStatisticsEntity teamStatistics : allByTournamentIdOrderByWinCount) {
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
        List<StatisticDTO> allByTournamentIdOrderByRedCard = repository.findAllByTournamentIdOrderByRedCard(groupId);

        return allByTournamentIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.RED_CARD))
                .toList();
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId) {
        List<StatisticDTO> allByTournamentIdOrderByRedCard = repository.findAllByTournamentIdOrderByYellowCard(groupId);

        return allByTournamentIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.YELLOW_CARD))
                .toList();
    }

    @Override
    public TeamStatisticsDTO save(TournamentEntity tournament, TeamEntity team) {
        TeamStatisticsEntityPK pk = new TeamStatisticsEntityPK(tournament, team);
        TeamStatisticsEntity saved = repository.save(new TeamStatisticsEntity(
                pk,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                null
        ));

        return saved.toDTO();
    }

    @Override
    public TeamStatisticsEntity save(TeamStatisticsEntity teamStatistics) {
        return repository.saveAndFlush(teamStatistics);
    }

    @Override
    public List<TeamDTO> findAllTeamByTournamentId(long groupId) {
        return repository.findAllTeamByTournamentId(groupId);
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

    @Override
    public List<TeamStatisticsEntity> findAllByTournamentIdOrderByWinCount(Long groupId) {
        return repository.findAllByTournamentIdOrderByWinCount(groupId);
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
