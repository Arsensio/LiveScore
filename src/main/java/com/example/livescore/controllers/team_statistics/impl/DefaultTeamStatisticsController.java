package com.example.livescore.controllers.team_statistics.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.team_statistics.TeamStatisticController;
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
        extends AbstractFootballController<TeamStatisticsService,
        TeamStatisticsDTO, SaveTeamStatisticsDTO, TeamStatisticsEntityPK>
        implements TeamStatisticController {

    public DefaultTeamStatisticsController(TeamStatisticsService service) {
        super(service);
    }

    @Override
    @GetMapping("/goals")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByGoals(@RequestParam("groupId") long groupId) {
        return new ResponseEntity<>(service.findTeamsSortedByGoals(groupId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/points")
    public ResponseEntity<List<TeamStatisticsDTO>> findAllSortedByPoints(@RequestParam("groupId") long groupId) {
        return new ResponseEntity<>(service.findTeamsSortedByPoints(groupId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/red_cards/{groupId}")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByRedCards(@PathVariable long groupId) {
        return new ResponseEntity<>(service.findTeamsSortedByRedCards(groupId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/yellow_cards/{groupId}")
    public ResponseEntity<List<DistinctTeamStatisticsDTO>> findAllSortedByYellowCards(@PathVariable long groupId) {
        return new ResponseEntity<>(service.findTeamsSortedByYellowCard(groupId), HttpStatus.OK);
    }
}
