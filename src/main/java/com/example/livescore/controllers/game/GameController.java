package com.example.livescore.controllers.game;

import com.example.core.controller.FootballController;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.NewGameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GameController extends FootballController<GameDTO, SaveGameDTO, Long> {

    ResponseEntity<List<GameDTO>> findAllByDate(String date);

    ResponseEntity<List<NewGameDTO>> newFindAllByDate(String date, List<Long> tournaments);

    ResponseEntity<List<NewGameDTO>> findAllAdminGameByDate(String date, String token);

    ResponseEntity<List<NewGameDTO>> findAllLiveMatches();

    ResponseEntity<GameDTO> startMatch(Long id);

    ResponseEntity<GameDTO> endMatch(Long id);
}
