package com.example.livescore.service.player.impl;


import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.PlayerRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.players.MinPlayerDto;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.players.UpdatePlayerDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DefaultPlayerService
        extends AbstractFootballService<PlayerEntity, PlayerDTO, SavePlayerDTO, Long, PlayerRepository>
        implements PlayerService {

    private final TeamFootballService teamFootballService;
    private final PlayerStatisticsService playerStatisticsService;
    private final GroupService groupService;
    private final TournamentService tournamentService;

    public DefaultPlayerService(PlayerRepository playerRepository, TeamFootballService teamFootballService, PlayerStatisticsService playerStatisticsService, GroupService groupService, TournamentService tournamentService) {
        super(playerRepository);
        this.teamFootballService = teamFootballService;
        this.playerStatisticsService = playerStatisticsService;
        this.groupService = groupService;
        this.tournamentService = tournamentService;
    }

    @Override
    @Transactional
    public PlayerDTO save(SavePlayerDTO savePlayerDTO) {
        PlayerEntity save = repository.save(new PlayerEntity(
                null,
                teamFootballService.findEntityById(savePlayerDTO.getTeamId()),
                savePlayerDTO.getName(),
                savePlayerDTO.getSurname(),
                savePlayerDTO.getPlayerNumber(),
                savePlayerDTO.getRole()
        ));
        TournamentEntity tournament = tournamentService.findEntityById(1);
        playerStatisticsService.saveDefault(save, tournament);
        return save.toDTO();
    }

    @Override
    public PlayerDTO update(Long id, SavePlayerDTO savePlayerDTO) {
        repository.findById(id).ifPresentOrElse(playerEntity -> {
            playerEntity.setName(savePlayerDTO.getName());
            playerEntity.setSurname(savePlayerDTO.getSurname());
            playerEntity.setRole(savePlayerDTO.getRole());
            playerEntity.setTeam(teamFootballService.findEntityById(savePlayerDTO.getTeamId()));
            playerEntity.setPlayerNumber(savePlayerDTO.getPlayerNumber());
            repository.saveAndFlush(playerEntity);
        }, () -> {
            throw ResourceNotFoundException.build(id, "Player");
        });
        return repository.findById(id).get().toDTO();
    }

    @Override
    public List<PlayerDTO> findAllByTeamId(long teamId) {
        return repository.findAllByTeamId(teamId)
                .stream()
                .map(PlayerEntity::toDTO)
                .toList();
    }

    @Override
    public PlayerEntity findEntityById(long playerId) {
        Optional<PlayerEntity> player = repository.findById(playerId);
        if (player.isEmpty()) {
            throw ResourceNotFoundException.build(playerId, "PlayerEntity");
        } else {
            return player.get();
        }
    }

    @Override
    public List<MinPlayerDto> findAllPlayersOfTeam(Long teamId) {
        return repository.findAllPlayersOfTeam(teamId);
    }

    @Override
    public List<PlayerDTO> transferPlayers(List<UpdatePlayerDTO> playersToUpdate) {
        List<PlayerDTO> updatedPlayers = new ArrayList<>();

        for (UpdatePlayerDTO p : playersToUpdate) {
            PlayerEntity player = findEntityById(p.getPlayerId());
            TeamEntity newTeam = teamFootballService.findEntityById(p.getNewTeamId());
            List<Integer> allPlayerNumber = findAllPlayerNumberByTeamId(newTeam.getTeamId());
            Integer newNumber = player.getPlayerNumber();
            if (allPlayerNumber.contains(newNumber)) {
                newNumber = generateRandomNumExcludeList(allPlayerNumber);
            }

            player.setTeam(newTeam);
            player.setPlayerNumber(newNumber);

            PlayerEntity updatedPlayer = repository.saveAndFlush(player);
            updatedPlayers.add(updatedPlayer.toDTO());
        }

        return updatedPlayers;
    }


    private PlayerEntity findEntityById(Long id) {
        Optional<PlayerEntity> foundEntity = repository.findById(id);
        if (foundEntity.isEmpty()) {
            throw ResourceNotFoundException.build(id, "TeamEntity");
        } else
            return foundEntity.get();
    }

    private List<Integer> findAllPlayerNumberByTeamId(Long teamId) {
        List<PlayerEntity> allByTeamId = repository.findAllByTeamId(teamId);

        return allByTeamId.stream()
                .map(PlayerEntity::getPlayerNumber)
                .toList();
    }

    private Integer generateRandomNumExcludeList(List<Integer> excludedValues) {
        Random rand = new Random();
        int randomNum;
        do {
            randomNum = rand.nextInt(99) + 1;
        } while (excludedValues.contains(randomNum));

        return randomNum;
    }
}
