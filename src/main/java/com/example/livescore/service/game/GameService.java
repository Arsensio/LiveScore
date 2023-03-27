package com.example.livescore.service.game;

import com.example.core.service.FootballService;
import com.example.livescore.models.GameEntity;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;

import java.util.List;

public interface GameService extends FootballService<GameDTO, SaveGameDTO, Long> {
    List<GameDTO> findAllByDate(String date);

    List<GameDTO> findAllLiveMatches();

    GameDTO startMatch(Long gameId);

    GameEntity findEntityById(long id);
}
