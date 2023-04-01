package com.example.livescore.controllers.tournament.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.tournament.TournamentController;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/tournament")
public class DefaultTournamentController extends AbstractFootballController<TournamentService, TournamentDTO,
        SaveTournamentDTO, Long>
        implements TournamentController {

    public DefaultTournamentController(TournamentService service) {
        super(service);
    }


    @Override
    @GetMapping("/user")
    public ResponseEntity<List<TournamentDTO>> findAllByUserId(@PathParam("userId") long userId) {
        return new ResponseEntity<>(service.findAllByUserId(userId), HttpStatus.OK);
    }
}
