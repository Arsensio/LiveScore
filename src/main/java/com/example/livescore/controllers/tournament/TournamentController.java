package com.example.livescore.controllers.tournament;

import com.example.core.controller.FootballController;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TournamentController extends FootballController<TournamentDTO, SaveTournamentDTO, Long> {

    ResponseEntity<List<TournamentDTO>> findAllByUserId(long userId);

    ResponseEntity<TournamentDTO> createLeagueTournament(SaveTournamentDTO saveTournamentDTO);

    ResponseEntity<TournamentDTO> createCupTournament(SaveCupTournamentDTO saveCupTournamentDTO);

    ResponseEntity<List<TournamentDTO>> searchByName(String name);

}
