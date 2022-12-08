package com.example.livescore.service;


import com.example.livescore.models.PlayerEntity;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.store.PlayerStatisticsRepository;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerStatisticsServiceImpl implements MainService<SavePlayerStatisticsDTO, PlayerStatisticsDTO>{

    private final PlayerStatisticsRepository playerStatisticsRepository;

    @Override
    public List<PlayerStatisticsDTO> getAll() {
        return playerStatisticsRepository.findAll().stream().map(PlayerStatisticsEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public PlayerStatisticsDTO getIndividual(Long id) {
        return playerStatisticsRepository.getReferenceById(id).toDTO();
    }

    @Override
    public PlayerStatisticsDTO postIndividual(SavePlayerStatisticsDTO savePlayerStatisticsDTO) {
        return null;
    }

    @Override
    public PlayerStatisticsDTO putIndividual(Long id, SavePlayerStatisticsDTO savePlayerStatisticsDTO) {
        return null;
    }

    @Override
    public void deleteIndividual(Long id) {

    }
}
