package com.example.livescore.controllers.tournament.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.tournament.TournamentController;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournament")
public class DefaultTournamentController extends AbstractFootballController<TournamentService, TournamentDTO,
        SaveTournamentDTO, Long>
        implements TournamentController {

    public DefaultTournamentController(TournamentService service) {
        super(service);
    }
}
