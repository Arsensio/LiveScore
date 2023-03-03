package com.example.livescore.service;

import com.example.livescore.models.PlayerEntity;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.store.TeamRepository;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements MainService<SavePlayerDTO, PlayerDTO>{

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<PlayerDTO> getAll() {
        return playerRepository.findAll().stream().map(PlayerEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public PlayerDTO getIndividual(Long id) {
        return playerRepository.getReferenceById(id).toDTO();
    }

    @Override
    public PlayerDTO postIndividual(SavePlayerDTO savePlayerDTO) {
        return playerRepository.save(new PlayerEntity(
                null,
                teamRepository.findById(savePlayerDTO.getTeamId()).get(),
                savePlayerDTO.getName(),
                savePlayerDTO.getSurname(),
                savePlayerDTO.getPlayerNumber(),
                savePlayerDTO.getRole()
        )).toDTO();
    }

    @Override
    public PlayerDTO putIndividual(Long id, SavePlayerDTO savePlayerDTO) {
        playerRepository.findById(id).ifPresentOrElse(playerEntity -> {
            playerEntity.setName(savePlayerDTO.getName());
            playerEntity.setSurname(savePlayerDTO.getSurname());
            playerEntity.setRole(savePlayerDTO.getRole());
            playerEntity.setTeam(teamRepository.findById(savePlayerDTO.getPlayerId()).get());
            playerEntity.setPlayerNumber(savePlayerDTO.getPlayerNumber());
            playerRepository.saveAndFlush(playerEntity);
        },()->{
            throw new ResourceNotFoundException("There is no such Player");
        });
        return playerRepository.findById(id).get().toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        playerRepository.deleteById(id);
    }
}
