package com.example.livescore.controllers.team_statistics.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.team_statistics.TeamStatisticController;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team_statistics")
public class DefaultTeamStatisticsController
        extends AbstractFootballController<TeamStatisticsEntity, TeamStatisticsService, TeamStatisticsDTO, SaveTeamStatisticsDTO, TeamStatisticsEntityPK>
        implements TeamStatisticController {

    public DefaultTeamStatisticsController(TeamStatisticsService service) {
        super(service);
    }

    @Override
    @GetMapping("/goals")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByGoals(@RequestParam("tournament_id") long tournament_id) {
        return new ResponseEntity<>(service.findTeamsSortedByGoals(tournament_id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/red_cards/{tournament_id}")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByRedCards(@PathVariable long tournament_id) {
        return new ResponseEntity<>(service.findTeamsSortedByRedCards(tournament_id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/yellow_cards/{tournament_id}")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByYellowCards(@PathVariable long tournament_id) {
        return new ResponseEntity<>(service.findTeamsSortedByYellowCard(tournament_id), HttpStatus.OK);
    }
}
