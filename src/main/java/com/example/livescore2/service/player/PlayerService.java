package com.example.livescore2.service.player;

import com.example.core.service.FootballService;
import com.example.livescore2.web.players.PlayerDTO;
import com.example.livescore2.web.players.SavePlayerDTO;

public interface PlayerService extends FootballService<PlayerDTO, SavePlayerDTO,Long> {
}
