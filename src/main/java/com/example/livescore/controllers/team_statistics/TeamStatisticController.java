package com.example.livescore.controllers.team_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamStatisticController extends FootballController<TeamStatisticsDTO, SaveTeamStatisticsDTO,
        TeamStatisticsEntityPK> {

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByGoals(long groupId);

    ResponseEntity<List<TeamStatisticsDTO>> findAllSortedByPoints(long groupId);

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByRedCards(long groupId);

    ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByYellowCards(long groupId);
}
