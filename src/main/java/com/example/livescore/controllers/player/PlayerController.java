package com.example.livescore.controllers.player;

import com.example.core.controller.FootballController;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;

public interface PlayerController extends FootballController<PlayerDTO, SavePlayerDTO, Long> {
}
