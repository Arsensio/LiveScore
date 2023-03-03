package com.example.livescore.controllers.team_statistics.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team_statistics")
public class DefaultTeamStatisticsController extends AbstractFootballController<TeamStatisticsService,
        TeamStatisticsDTO, SaveTeamStatisticsDTO, TeamStatisticsEntityPK> {

    public DefaultTeamStatisticsController(TeamStatisticsService service) {
        super(service);
    }
}
