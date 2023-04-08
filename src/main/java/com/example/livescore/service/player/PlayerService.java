package com.example.livescore.service.player;

import com.example.core.service.FootballService;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.web.players.MinPlayerDto;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.players.UpdatePlayerDTO;

import java.util.List;

public interface PlayerService extends FootballService<PlayerDTO, SavePlayerDTO, Long> {

    List<PlayerDTO> findAllByTeamId(long teamId);

    PlayerEntity findEntityById(long playerId);

    List<MinPlayerDto> findAllPlayersOfTeam(Long teamId);

    List<PlayerDTO> transferPlayers(List<UpdatePlayerDTO> updatedPlayers);
}
