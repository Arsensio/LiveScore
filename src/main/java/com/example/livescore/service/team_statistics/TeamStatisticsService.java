package com.example.livescore.service.team_statistics;

import com.example.core.service.FootballService;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;

public interface TeamStatisticsService extends FootballService<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {
}
