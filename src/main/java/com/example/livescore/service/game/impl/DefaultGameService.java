package com.example.livescore.service.game.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.GameRepository;
import com.example.livescore.service.game.GameService;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.livescore.enums.GameState.NOT_STARTED;
import static com.example.livescore.enums.GameState.STARTED;

@Service
public class DefaultGameService
        extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository>
        implements GameService {

    private final TeamFootballService teamFootballService;
    private final GroupService groupService;
    private final ProtocolService protocolService;
    private final TeamStatisticsService teamStatisticsService;
    private final PlayerStatisticsService playerStatisticsService;

    public DefaultGameService(GameRepository repository, TeamFootballService teamFootballService, GroupService groupService, ProtocolService protocolService, TeamStatisticsService teamStatisticsService, PlayerStatisticsService playerStatisticsService) {
        super(repository);
        this.teamFootballService = teamFootballService;
        this.groupService = groupService;
        this.protocolService = protocolService;
        this.teamStatisticsService = teamStatisticsService;
        this.playerStatisticsService = playerStatisticsService;
    }

    @Override
    public List<GameDTO> findAllByDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date1 = LocalDateTime.parse(date + " 00:00", df);
        LocalDateTime date2 = date1.plusMinutes(1439);

        return repository.findAllByGameDate(date1, date2)
                .stream()
                .map(GameEntity::toDTO)
                .toList();
    }

    @Override
    public List<GameDTO> findAllLiveMatches() {
        return repository.findAllLiveGame()
                .stream()
                .map(GameEntity::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public GameDTO startMatch(Long gameId) {
        GameEntity gameEntity = findEntityById(gameId);
        if (gameEntity.getGameState() == STARTED) {
            return gameEntity.toDTO();
        }
        GroupEntity group = gameEntity.getGroup();
        TeamEntity team1 = gameEntity.getProtocol().getTeam1();
        TeamEntity team2 = gameEntity.getProtocol().getTeam2();

        repository.updateIsPlayed(gameId);
        increaseGameCount(group, team1);
        increaseGameCount(group, team2);
        gameEntity.setGameState(STARTED);

        return gameEntity.toDTO();
    }

    @Override
    public GameEntity findEntityById(long id) {
        Optional<GameEntity> foundGame = repository.findById(id);
        if (foundGame.isEmpty()) {
            throw ResourceNotFoundException.build(id, "GameEntity");
        }
        return foundGame.get();
    }

    @Override
    public GameDTO save(SaveGameDTO dto) {
        GroupEntity group = groupService.findEntityById(dto.getGroupId());
        GameEntity createdGame = createGameEntity(group);
        ProtocolEntity defaultProtocol = createDefaultProtocol(createdGame, dto);

        createdGame.setProtocol(defaultProtocol);

        return createdGame.toDTO();
    }

    private GameEntity createGameEntity(GroupEntity group) {
        GameEntity gameEntity = new GameEntity(
                null,
                NOT_STARTED,
                group,
                null
        );
        return repository.save(gameEntity);
    }

    private ProtocolEntity createDefaultProtocol(GameEntity savedEntity, SaveGameDTO dto) {
        ProtocolEntity protocol = new ProtocolEntity(
                null,
                savedEntity,
                teamFootballService.findEntityById(dto.getTeam1Id()),
                teamFootballService.findEntityById(dto.getTeam2Id()),
                dto.getDateTime(),
                0,
                0,
                null
        );
        return protocolService.saveAndFlush(protocol);
    }

    private void increaseGameCount(GroupEntity group, TeamEntity team) {
        TeamStatisticsEntityPK teamPK = new TeamStatisticsEntityPK(group, team);
        teamStatisticsService.incrementGameCount(teamPK);

        team.getPlayers()
                .forEach(player -> increaseGamePlayed(group, player));
    }

    private void increaseGamePlayed(GroupEntity group, PlayerEntity player) {
        PlayerStatisticsEntityPK playerPk = new PlayerStatisticsEntityPK(group, player);
        playerStatisticsService.incrementGamePlayed(playerPk);
    }
}
