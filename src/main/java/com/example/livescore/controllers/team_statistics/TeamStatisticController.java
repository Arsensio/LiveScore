package com.example.livescore.controllers.team_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;

public interface TeamStatisticController extends FootballController<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {
}
