package com.example.livescore2.service.player.impl;


import com.example.core.service.AbstractFootballService;
import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore2.models.PlayerEntity;
import com.example.livescore2.repository.PlayerRepository;
import com.example.livescore2.repository.TeamRepository;
import com.example.livescore2.service.player.PlayerService;
import com.example.livescore2.web.players.PlayerDTO;
import com.example.livescore2.web.players.SavePlayerDTO;
import org.springframework.stereotype.Service;


@Service
public class DefaultPlayerService extends AbstractFootballService<PlayerEntity, PlayerDTO, SavePlayerDTO, PlayerRepository> implements PlayerService {

    private final TeamRepository teamRepository;

    public DefaultPlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        super(playerRepository);
        this.teamRepository = teamRepository;
    }

    @Override
    public PlayerDTO save(SavePlayerDTO savePlayerDTO) {
        return repository.save(new PlayerEntity(
                null,
                teamRepository.findById(savePlayerDTO.getTeamId()).get(),
                savePlayerDTO.getName(),
                savePlayerDTO.getSurname(),
                savePlayerDTO.getPlayerNumber(),
                savePlayerDTO.getRole()
        )).toDTO();
    }

    @Override
    public PlayerDTO update(long id, SavePlayerDTO savePlayerDTO) {
        repository.findById(id).ifPresentOrElse(playerEntity -> {
            playerEntity.setName(savePlayerDTO.getName());
            playerEntity.setSurname(savePlayerDTO.getSurname());
            playerEntity.setRole(savePlayerDTO.getRole());
            playerEntity.setTeam(teamRepository.findById(savePlayerDTO.getPlayerId()).get());
            playerEntity.setPlayerNumber(savePlayerDTO.getPlayerNumber());
            repository.saveAndFlush(playerEntity);
        },()->{
            throw new ResourceNotFoundException("There is no such Player");
        });
        return repository.findById(id).get().toDTO();
    }
}
