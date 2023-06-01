package com.example.livescore.controllers.player.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.player.PlayerController;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.players.UpdatePlayerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/player")
public class DefaultPlayerController
        extends AbstractFootballController<PlayerEntity, PlayerService, PlayerDTO, SavePlayerDTO, Long>
        implements PlayerController {

    public DefaultPlayerController(PlayerService service) {
        super(service);
    }

    @GetMapping("/team/{team_id}")
    @Override
    public ResponseEntity<List<PlayerDTO>> allPlayerByTeamId(@PathVariable("team_id") long teamId) {
        return new ResponseEntity<>(service.findAllByTeamId(teamId), OK);
    }

    @Override
    @PutMapping("/update/players")
    public ResponseEntity<List<PlayerDTO>> transferPlayers(@RequestBody List<UpdatePlayerDTO> updatedPlayers) {
        return new ResponseEntity<>(service.transferPlayers(updatedPlayers), OK);
    }
}
