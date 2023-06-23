package com.example.livescore.controllers.tournament;

import com.example.core.controller.FootballController;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TournamentController extends FootballController<TournamentDTO, SaveTournamentDTO, Long> {

    ResponseEntity<List<TournamentDTO>> findAllByUserId(String token);

    ResponseEntity<List<TournamentDTO>> findAllNotFinishedByUserId(String token);

    ResponseEntity<List<TournamentDTO>> findAllCupTournamentByUser(String token);

    ResponseEntity<TournamentDTO> createLeagueTournament(SaveTournamentDTO saveTournamentDTO, String token);

    ResponseEntity<TournamentDTO> createCupTournament(SaveCupTournamentDTO saveCupTournamentDTO, String token);

    ResponseEntity<List<TournamentDTO>> searchByName(String name);

}
