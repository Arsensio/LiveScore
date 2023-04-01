package com.example.livescore.controllers.player_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface PlayerStatisticsController
        extends FootballController<PlayerStatisticsDTO, SavePlayerStatisticsDTO, PlayerStatisticsEntityPK> {

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByGoals(long groupId);

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByYellowCard(long groupId);

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByRedCard(long groupId);

    public ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByAssists(long groupId);
}
