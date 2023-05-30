package com.example.livescore.controllers.player_statistics.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.player_statistics.PlayerStatisticsController;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player_statistics")
public class DefaultPlayerStatisticsController
        extends AbstractFootballController<PlayerStatisticsService, PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK>
        implements PlayerStatisticsController {

    public DefaultPlayerStatisticsController(PlayerStatisticsService service) {
        super(service);
    }

    @Override
    @GetMapping("/goals")
    public ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByGoals(@RequestParam("tournament_id") long tournament_id) {
        return new ResponseEntity<>(service.findAllByGoals(tournament_id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/assists")
    public ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByAssists(@RequestParam("tournament_id") long tournament_id) {
        return new ResponseEntity<>(service.findAllByAssists(tournament_id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/yellow_card")
    public ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByYellowCard(@RequestParam("tournament_id") long tournament_id) {
        return new ResponseEntity<>(service.findAllByYellowCard(tournament_id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/red_card")
    public ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByRedCard(@RequestParam("tournament_id") long tournament_id) {
        return new ResponseEntity<>(service.findAllByRedCard(tournament_id), HttpStatus.OK);
    }
}
