package com.example.livescore.service.team_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;

import java.util.List;

public interface TeamStatisticsService extends FootballService<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {
    List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId);

    List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId);

    List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId);
}
