package com.example.livescore.service.game;

import com.example.core.service.FootballService;
import com.example.livescore.models.GameEntity;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.NewGameDTO;
import com.example.livescore.web.games.SaveGameDTO;

import java.util.List;

public interface GameService extends FootballService<GameEntity, GameDTO, SaveGameDTO, Long> {

    List<GameDTO> findAllByDate(String date);

    List<NewGameDTO> newFindAllByDate(String date, List<Long> tournaments);

    List<NewGameDTO> findAllLiveMatches();

    GameDTO startMatch(Long gameId);

    GameDTO endMatch(Long id);

    List<NewGameDTO> findAllAdminGameByDate(String date, String token);
}
