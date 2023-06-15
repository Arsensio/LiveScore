package com.example.livescore.controllers.tournament.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.tournament.TournamentController;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tournament")
public class DefaultTournamentController
        extends AbstractFootballController<TournamentEntity, TournamentService, TournamentDTO, SaveTournamentDTO, Long>
        implements TournamentController {

    public DefaultTournamentController(TournamentService service) {
        super(service);
    }


    @Override
    @GetMapping("/user")
    public ResponseEntity<List<TournamentDTO>> findAllByUserId(@PathParam("userId") long userId) {
        return new ResponseEntity<>(service.findAllByUserId(userId), OK);
    }

    @Override
    @GetMapping("/user/no_finished")
    public ResponseEntity<List<TournamentDTO>> findAllNotFinishedByUserId(@PathParam("userId") long userId) {
        return new ResponseEntity<>(service.findAllNotFinishedByUserId(userId), OK);
    }

    @Override
    @GetMapping("/user/cup")
    public ResponseEntity<List<TournamentDTO>> findAllCupTournamentByUser(@PathParam("userId") long userId) {
        return new ResponseEntity<>(service.findAllCupTournamentByUser(userId), OK);
    }

    @Override
    @PostMapping("/league")
    public ResponseEntity<TournamentDTO> createLeagueTournament(@RequestBody SaveTournamentDTO saveTournamentDTO, @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(service.createLeague(saveTournamentDTO, token), OK);
    }

    @Override
    @PostMapping("/cup")
    public ResponseEntity<TournamentDTO> createCupTournament(@RequestBody SaveCupTournamentDTO dto, @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(service.createCup(dto, token), OK);
    }

    @Override
    @GetMapping("/tournament_name")
    public ResponseEntity<List<TournamentDTO>> searchByName(@PathParam("") String name) {
        return new ResponseEntity<>(service.searchByName(name), OK);
    }
}
