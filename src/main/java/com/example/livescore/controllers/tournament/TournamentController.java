package com.example.livescore.controllers.tournament;

import com.example.core.controller.FootballController;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;

public interface TournamentController extends FootballController<TournamentDTO, SaveTournamentDTO, Long> {
}
