package com.example.livescore.service;

import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements MainService<SavePlayerDTO, PlayerDTO>{

    private final PlayerRepository playerRepository;

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
        return null;
    }

    @Override
    public PlayerDTO putIndividual(Long id, SavePlayerDTO savePlayerDTO) {
        return null;
    }

    @Override
    public void deleteIndividual(Long id) {

    }
}
