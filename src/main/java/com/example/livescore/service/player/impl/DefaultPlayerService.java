package com.example.livescore.service.player.impl;


import com.example.core.service.AbstractFootballService;
import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.repository.PlayerRepository;
import com.example.livescore.repository.TeamRepository;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlayerService extends AbstractFootballService<PlayerEntity, PlayerDTO, SavePlayerDTO, Long,
        PlayerRepository> implements PlayerService {

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
    public PlayerDTO update(Long id, SavePlayerDTO savePlayerDTO) {
        repository.findById(id).ifPresentOrElse(playerEntity -> {
            playerEntity.setName(savePlayerDTO.getName());
            playerEntity.setSurname(savePlayerDTO.getSurname());
            playerEntity.setRole(savePlayerDTO.getRole());
            playerEntity.setTeam(teamRepository.findById(savePlayerDTO.getPlayerId()).get());
            playerEntity.setPlayerNumber(savePlayerDTO.getPlayerNumber());
            repository.saveAndFlush(playerEntity);
        }, () -> {
            throw new ResourceNotFoundException("There is no such Player");
        });
        return repository.findById(id).get().toDTO();
    }
}
