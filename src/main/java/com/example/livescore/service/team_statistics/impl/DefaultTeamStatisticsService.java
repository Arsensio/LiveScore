package com.example.livescore.service.team_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.models.*;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.*;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.livescore.enums.EventEnum.*;

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
                .map(team -> team.toDistinctStatisticsDTO(GOAL.getEventName()))
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
        TeamStatisticsEntity saved = repository.save(getDefaultTeamStatisticsEntity(pk));
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
        return repository.findAllByTournamentIdOrderByWinCount(tournamentId);
    }

    @Override
    public TeamStatisticsEntity findEntityByTournamentAndTeam(TournamentEntity tournament, TeamEntity team) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(tournament, team);
        return findEntityById(teamStatisticsEntityPK);
    }

    @Override
    public TeamStatisticsEntity saveAndFlash(TournamentEntity tournament, TeamEntity team, GroupEntity group) {
        TeamStatisticsEntity foundTeamStat = this.findEntityByTournamentAndTeam(tournament, team);
        foundTeamStat.setGroup(group);
        return repository.saveAndFlush(foundTeamStat);
    }

    @Override
    public List<TopFiveTeamStatistics> findAllTopFiveStatistics(long tournament_id) {
        List<TopFiveTeamStatistics> teamStatistics = new ArrayList<>();
        teamStatistics.add(getDefaultTopFiveTeamStatistics(GOAL, findTeamsSortedByGoals(tournament_id)));
        teamStatistics.add(getDefaultTopFiveTeamStatistics(RED_CARD, findTeamsSortedByRedCards(tournament_id)));
        teamStatistics.add(getDefaultTopFiveTeamStatistics(YELLOW_CARD, findTeamsSortedByYellowCard(tournament_id)));

        return teamStatistics;
    }

    private TopFiveTeamStatistics getDefaultTopFiveTeamStatistics(EventEnum goal, List<DistinctTeamStatisticsDTO> tournament_id) {
        return new TopFiveTeamStatistics(goal.getEventName(), getTopFiveElement(tournament_id));
    }

    private List<DistinctTeamStatisticsDTO> getTopFiveElement(List<DistinctTeamStatisticsDTO> list) {
        return list.stream()
                .limit(5)
                .toList();
    }

    private static TeamStatisticsEntity getDefaultTeamStatisticsEntity(TeamStatisticsEntityPK pk) {
        return new TeamStatisticsEntity(
                pk,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                null
        );
    }
}
