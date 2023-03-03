package com.example.livescore.controllers.game;

import com.example.core.controller.FootballController;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;

public interface GameController extends FootballController<GameDTO, SaveGameDTO,Long> {
}
