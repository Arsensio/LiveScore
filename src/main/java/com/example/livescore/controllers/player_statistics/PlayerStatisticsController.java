package com.example.livescore.controllers.player_statistics;

import com.example.core.controller.FootballController;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PlayerStatisticsController extends FootballController<PlayerStatisticsDTO, SavePlayerStatisticsDTO,
        PlayerStatisticsEntityPK> {

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByGoals(long tournament_id);

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByYellowCard(long tournament_id);

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByRedCard(long tournament_id);

    ResponseEntity<List<DistinctPlayerStatisticsDTO>> findAllByAssists(long tournament_id);
}
