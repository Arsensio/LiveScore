package com.example.livescore.service.team_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.*;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teams.TeamDTO;

import java.util.List;

public interface TeamStatisticsService extends FootballService<TeamStatisticsEntity, TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {

    List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId);

    TeamStatisticsDTO save(TournamentEntity tournament, TeamEntity team);

    TeamStatisticsEntity save(TeamStatisticsEntity teamStatistics);

    List<TeamDTO> findAllTeamByTournamentId(long groupId);

    List<TeamDTO> findAllTeamByGroupIdAndTournamentId(long tournamentId, long groupId);

    void incrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void incrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void decrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void decrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void incrementGameCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    List<TeamStatisticsEntity> findAllByTournamentIdOrderByWinCount(Long groupId);

    TeamStatisticsEntity findEntityByTournamentAndTeam(TournamentEntity tournament, TeamEntity team);

    TeamStatisticsEntity saveAndFlash(TournamentEntity tournament, TeamEntity team, GroupEntity group);
}
