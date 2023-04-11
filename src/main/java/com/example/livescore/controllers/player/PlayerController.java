package com.example.livescore.controllers.player;

import com.example.core.controller.FootballController;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.players.UpdatePlayerDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlayerController extends FootballController<PlayerDTO, SavePlayerDTO, Long> {

    ResponseEntity<List<PlayerDTO>> allPlayerByTeamId(long teamId);

    ResponseEntity<List<PlayerDTO>> transferPlayers(List<UpdatePlayerDTO> updatedPlayers);
}
