package com.example.livescore.service.team_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teams.TeamDTO;

import java.util.List;

public interface TeamStatisticsService extends FootballService<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {
    List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId);

    List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId);

    TeamStatisticsDTO save(GroupEntity group, TeamEntity team);

    List<TeamDTO> findAllTeamByGroupId(long groupId);

    void incrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void incrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void decrementGoalCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void decrementGoalMissedCount(TeamStatisticsEntityPK teamStatisticsEntityPK);

    void incrementGameCount(TeamStatisticsEntityPK teamStatisticsEntityPK);
}
