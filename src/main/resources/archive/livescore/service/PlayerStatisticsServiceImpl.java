package com.example.livescore.service;


import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.store.GroupRepository;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.store.PlayerStatisticsRepository;
import com.example.livescore.web.playerStatistics.InitPlayerStatisticDTO;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerStatisticsServiceImpl {

    private final PlayerStatisticsRepository playerStatisticsRepository;
    private final GroupRepository groupRepository;
    private final PlayerRepository playerRepository;

    public List<PlayerStatisticsDTO> getAllByGoal(Long groupId) {
        return playerStatisticsRepository.getPlayerStatisticsEntitiesByGroupIdAndGoals(groupId).stream().map(PlayerStatisticsEntity::toDTO).collect(Collectors.toList());
    }

    public PlayerStatisticsDTO getIndividual(Long group, Long player) {
        return playerStatisticsRepository.findPlayerStatisticsEntitiesById(new PlayerStatisticsEntityPK(groupRepository.findById(group).get(), playerRepository.findById(player).get())).toDTO();
    }

    public PlayerStatisticsDTO postIndividual(InitPlayerStatisticDTO initPlayerStatisticDTO) {
        return playerStatisticsRepository.save(new PlayerStatisticsEntity(
                new PlayerStatisticsEntityPK(groupRepository.findById(initPlayerStatisticDTO.getGroupId()).get(), playerRepository.findById(initPlayerStatisticDTO.getPlayerId()).get()),
                0L,
                0L,
                0L,
                0L,
                0L
        )).toDTO();
    }

    public PlayerStatisticsDTO putIndividual(Long group, Long player, SavePlayerStatisticsDTO savePlayerStatisticsDTO) {
        PlayerStatisticsEntity playerStatistics = playerStatisticsRepository.findPlayerStatisticsEntitiesById(new PlayerStatisticsEntityPK(groupRepository.findById(group).get(), playerRepository.findById(player).get()));
        if (playerStatistics != null) {
            playerStatistics.setMatchPlayed(savePlayerStatisticsDTO.getMatchPlayed());
            playerStatistics.setAssists(savePlayerStatisticsDTO.getAssists());
            playerStatistics.setYellowCard(savePlayerStatisticsDTO.getYellowCard());
            playerStatistics.setRedCard(savePlayerStatisticsDTO.getRedCard());
            playerStatistics.setGoals(savePlayerStatisticsDTO.getGoals());
            playerStatisticsRepository.saveAndFlush(playerStatistics);
        } else {
            throw new ResourceNotFoundException("There is no such Players Statistics");
        }
        return playerStatistics.toDTO();
    }

    public void deleteIndividual(Long id) {
        playerStatisticsRepository.deleteById(id);
    }

    public List<PlayerStatisticsDTO> getAllByAssists(Long groupId) {
        return playerStatisticsRepository.getPlayerStatisticsEntitiesByGroupIdAndAssists(groupId).stream().map(PlayerStatisticsEntity::toDTO).collect(Collectors.toList());
    }
}
