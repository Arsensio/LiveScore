package com.example.livescore.controllers.team_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TopFiveTeamStatistics;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamStatisticController extends FootballController<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByGoals(long tournament_id);

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByRedCards(long tournament_id);

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByYellowCards(long tournament_id);

    ResponseEntity<List<TopFiveTeamStatistics>> findAllTopFiveStatistics(long tournament_id);
}
