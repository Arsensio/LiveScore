package com.example.livescore.service.player;

import com.example.core.service.FootballService;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;

public interface PlayerService extends FootballService<PlayerDTO, SavePlayerDTO, Long> {
}
