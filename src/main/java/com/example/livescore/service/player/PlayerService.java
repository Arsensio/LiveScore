package com.example.livescore.service.player;

import com.example.core.service.FootballService;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;

import java.util.List;

public interface PlayerService extends FootballService<PlayerDTO, SavePlayerDTO, Long> {

    List<PlayerDTO> findAllByTeamId(long teamId);

    PlayerEntity findEntityById(long playerId);
}
