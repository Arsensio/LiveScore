package com.example.livescore.service.player.impl;


import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.PlayerRepository;
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

import java.util.*;

@Service
public class DefaultPlayerService
        extends AbstractFootballService<PlayerEntity, PlayerDTO, SavePlayerDTO, Long, PlayerRepository>
        implements PlayerService {

    private final TeamFootballService teamFootballService;
    private final PlayerStatisticsService playerStatisticsService;
    private final TournamentService tournamentService;

    public DefaultPlayerService(PlayerRepository playerRepository, TeamFootballService teamFootballService, PlayerStatisticsService playerStatisticsService, TournamentService tournamentService) {
        super(playerRepository);
        this.teamFootballService = teamFootballService;
        this.playerStatisticsService = playerStatisticsService;
        this.tournamentService = tournamentService;
    }

    @Override
    @Transactional
    public PlayerDTO save(SavePlayerDTO savePlayerDTO) {
//        checkPlayerNumberForExistence(savePlayerDTO.getPlayerNumber(), savePlayerDTO.getTeamId());
        PlayerEntity save = repository.save(getDefaultPlayerEntity(savePlayerDTO));
        TournamentEntity tournament = tournamentService.findEntityById(savePlayerDTO.getTournamentId());
        playerStatisticsService.save(save, tournament);
        return save.toDTO();
    }

    @Override
    public PlayerDTO update(Long id, SavePlayerDTO savePlayerDTO) {
        checkPlayerNumberForExistence(savePlayerDTO.getPlayerNumber(), savePlayerDTO.getTeamId());

        PlayerEntity playerEntity = findEntityById(id);
        updatePlayerEntity(playerEntity, savePlayerDTO);

        return repository.saveAndFlush(playerEntity).toDTO();
    }

    @Override
    public List<PlayerDTO> findAllByTeamId(long teamId) {
        return repository.findAllByTeamId(teamId)
                .stream()
                .map(PlayerEntity::toDTO)
                .toList();
    }

    @Override
    public List<MinPlayerDto> findAllPlayersOfTeam(Long teamId) {
        return repository.findAllPlayersOfTeam(teamId);
    }

    @Override
    public List<PlayerDTO> transferPlayers(List<UpdatePlayerDTO> playersToUpdate) {
        return playersToUpdate.stream()
                .map(this::updatePlayer)
                .filter(Objects::nonNull)
                .map(PlayerEntity::toDTO)
                .toList();
    }

    @Override
    public List<Integer> findAllPlayerNumbersInTeam(Long teamId) {
        return repository.findAllPlayerNumbersInTeam(teamId);
    }

    @Override
    public void checkPlayerNumberForExistence(Integer playerNumber, Long teamId) {
        List<Integer> allNumbersInPlayerTeam = findAllPlayerNumbersInTeam(teamId);
        if (allNumbersInPlayerTeam.contains(playerNumber)) {
            throw new RuntimeException("Игрок с номером: " + playerNumber + " в команде: "
                    + teamFootballService.getTeamNameById(teamId) + " уже существует!");
        }
    }

    private List<Integer> findAllPlayerNumberByTeamId(Long teamId) {
        List<PlayerEntity> allByTeamId = repository.findAllByTeamId(teamId);

        return allByTeamId.stream()
                .map(PlayerEntity::getPlayerNumber)
                .toList();
    }

    private Integer generateRandomNumExcludeList(List<Integer> excludedValues) {
        Random rand = new Random();
        Set<Integer> excludedSet = new HashSet<>(excludedValues);

        int randomNum = rand.nextInt(99) + 1;
        while (excludedSet.contains(randomNum)) {
            randomNum = rand.nextInt(99) + 1;
        }

        return randomNum;
    }

    private PlayerEntity getDefaultPlayerEntity(SavePlayerDTO savePlayerDTO) {
        return new PlayerEntity(
                null,
                teamFootballService.findEntityById(savePlayerDTO.getTeamId()),
                savePlayerDTO.getName(),
                savePlayerDTO.getSurname(),
                savePlayerDTO.getPlayerNumber(),
                savePlayerDTO.getRole()
        );
    }

    private PlayerEntity updatePlayer(UpdatePlayerDTO updatePlayerDTO) {
        PlayerEntity player = findEntityById(updatePlayerDTO.getPlayerId());

        if (Objects.equals(player.getTeam().getTeamId(), updatePlayerDTO.getNewTeamId())) {
            return null;
        }

        TeamEntity newTeam = teamFootballService.findEntityById(updatePlayerDTO.getNewTeamId());
        List<Integer> allPlayerNumbers = findAllPlayerNumberByTeamId(newTeam.getTeamId());
        Integer newNumber = player.getPlayerNumber();

        if (allPlayerNumbers.contains(newNumber)) {
            newNumber = generateRandomNumExcludeList(allPlayerNumbers);
        }

        player.setTeam(newTeam);
        player.setPlayerNumber(newNumber);

        return repository.saveAndFlush(player);
    }

    private void updatePlayerEntity(PlayerEntity playerEntity, SavePlayerDTO savePlayerDTO) {
        playerEntity.setName(savePlayerDTO.getName());
        playerEntity.setSurname(savePlayerDTO.getSurname());
        playerEntity.setRole(savePlayerDTO.getRole());
        playerEntity.setTeam(teamFootballService.findEntityById(savePlayerDTO.getTeamId()));
        playerEntity.setPlayerNumber(savePlayerDTO.getPlayerNumber());
    }
}
