package com.example.livescore.service.game;

import com.example.core.service.FootballService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GameService extends FootballService<GameDTO, SaveGameDTO,Long> {
    List<GameDTO> findAllByDate(String date);

    GameDTO startMatch(Long gameId);
}
