package com.example.livescore.service.game;

import com.example.core.service.FootballService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;

public interface GameService extends FootballService<GameDTO, SaveGameDTO,Long> {
}
