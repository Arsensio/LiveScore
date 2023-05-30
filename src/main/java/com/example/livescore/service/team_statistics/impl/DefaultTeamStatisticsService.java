package com.example.livescore.service.team_statistics.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.models.*;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.StatisticDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultTeamStatisticsService
        extends AbstractFootballService<TeamStatisticsEntity, TeamStatisticsDTO, SaveTeamStatisticsDTO, TeamStatisticsEntityPK, TeamStatisticsRepository>
        implements TeamStatisticsService {

    public DefaultTeamStatisticsService(TeamStatisticsRepository repository) {
        super(repository);
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId) {
        return repository.findAllByTournamentIdOrderByGoalCount(groupId)
                .stream()
                .map(team -> team.toDistinctStatisticsDTO(EventEnum.GOAL.getEventName()))
                .toList();
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId) {
        List<StatisticDTO> allByTournamentIdOrderByRedCard = repository.findAllByTournamentIdOrderByRedCard(groupId);

        return allByTournamentIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventEnum.RED_CARD))
                .toList();
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId) {
        List<StatisticDTO> allByTournamentIdOrderByRedCard = repository.findAllByTournamentIdOrderByYellowCard(groupId);

        return allByTournamentIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventEnum.YELLOW_CARD))
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
    public List<TeamDTO> findAllTeamByGroupIdAndTournamentId(long tournamentId, long groupId) {
        return repository.findAllTeamByGroupIdAndTournamentId(tournamentId, groupId);
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
    public List<TeamStatisticsEntity> findAllByTournamentIdOrderByWinCount(Long tournamentId) {
        List<TeamStatisticsEntity> allByTournamentIdOrderByWinCount = repository.findAllByTournamentIdOrderByWinCount(tournamentId);
        return allByTournamentIdOrderByWinCount;
    }

    @Override
    public TeamStatisticsEntity findEntityById(TournamentEntity tournament, TeamEntity team) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(tournament, team);
        Optional<TeamStatisticsEntity> byId = repository.findById(teamStatisticsEntityPK);
        if (byId.isEmpty()) {
            throw ResourceNotFoundException.build(teamStatisticsEntityPK, "TeamStatisticsEntity");
        }
        return byId.get();
    }

    @Override
    public TeamStatisticsEntity saveAndFlash(TournamentEntity tournament, TeamEntity team, GroupEntity group) {
        TeamStatisticsEntity foundTeamStat = this.findEntityById(tournament, team);
        foundTeamStat.setGroup(group);
        return repository.saveAndFlush(foundTeamStat);
    }
}
