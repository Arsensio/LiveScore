package com.example.livescore.service.game.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.GameRepository;
import com.example.livescore.service.game.GameService;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.NewGameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.livescore.enums.GameState.*;

@Service
public class DefaultGameService
        extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository>
        implements GameService {

    private final TeamFootballService teamFootballService;
    private final GroupService groupService;
    private final ProtocolService protocolService;
    private final TeamStatisticsService teamStatisticsService;
    private final PlayerStatisticsService playerStatisticsService;

    private final GroupInfoService groupInfoService;

    public DefaultGameService(GameRepository repository, TeamFootballService teamFootballService, GroupService groupService, ProtocolService protocolService, TeamStatisticsService teamStatisticsService, PlayerStatisticsService playerStatisticsService, GroupInfoService groupInfoService) {
        super(repository);
        this.teamFootballService = teamFootballService;
        this.groupService = groupService;
        this.protocolService = protocolService;
        this.teamStatisticsService = teamStatisticsService;
        this.playerStatisticsService = playerStatisticsService;
        this.groupInfoService = groupInfoService;
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
    public List<NewGameDTO> newFindAllByDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date1 = LocalDateTime.parse(date + " 00:00", df);
        LocalDateTime date2 = date1.plusMinutes(1439);

        List<GameEntity> allGameByDate = repository.findAllByGameDate(date1, date2);
        List<GroupEntity> allGroups = groupService.findAllEntity();

        return findGameByGroup(allGroups, allGameByDate);
    }


    @Override
    public List<NewGameDTO> findAllLiveMatches() {
        List<GameEntity> allLiveGame = repository.findAllLiveGame();
        List<GroupEntity> allGroups = groupService.findAllEntity();

        return findGameByGroup(allGroups, allLiveGame);

    }

    @Override
    @Transactional
    public GameDTO startMatch(Long gameId) {
        GameEntity gameEntity = findEntityById(gameId);
        if (gameEntity.getGameState() == STARTED || gameEntity.getGameState() == ENDED) {
            return gameEntity.toDTO();
        }
        TournamentEntity tournament = gameEntity.getGroup().getTournament();
        GroupEntity group = gameEntity.getGroup();
        TeamEntity team1 = gameEntity.getProtocol().getTeam1();
        TeamEntity team2 = gameEntity.getProtocol().getTeam2();

        repository.updateIsPlayed(gameId);
        increaseGameCount(tournament, team1, group);
        increaseGameCount(tournament, team2, group);
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
    public GameDTO endMatch(Long id) {
        GameEntity gameEntity = findEntityById(id);
        if (gameEntity.getGameState() == ENDED) {
            return gameEntity.toDTO();
        }
        GroupEntity group = gameEntity.getGroup();
        List<TeamStatisticsEntity> allByTournamentIdOrderByWinCount = teamStatisticsService.findAllByTournamentIdOrderByWinCount(group.getGroupId());
        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();

        for (TeamStatisticsEntity teamStatistics : allByTournamentIdOrderByWinCount) {
            for (ProtocolEntity protocolEntity : allByGameStateStarted) {
                int team1Score = protocolEntity.getTeam1Score();
                int team2Score = protocolEntity.getTeam2Score();

                if (teamStatistics.getId().getTeam() == protocolEntity.getTeam1()) {
                    GroupInfoEntity groupInfo = groupInfoService.findEntityByGroupAndTeamId(protocolEntity.getGame().getGroup().getTournament(), protocolEntity.getGame().getGroup(), protocolEntity.getTeam1());
                    updatePointsAndStatistic(team1Score, team2Score, teamStatistics, groupInfo);

                    groupInfoService.saveAndFlash(groupInfo);
                    teamStatisticsService.save(teamStatistics);
                } else if (teamStatistics.getId().getTeam() == protocolEntity.getTeam2()) {
                    GroupInfoEntity groupInfo = groupInfoService.findEntityByGroupAndTeamId(protocolEntity.getGame().getGroup().getTournament(), protocolEntity.getGame().getGroup(), protocolEntity.getTeam2());
                    updatePointsAndStatistic(team2Score, team1Score, teamStatistics, groupInfo);

                    groupInfoService.saveAndFlash(groupInfo);
                    teamStatisticsService.save(teamStatistics);
                }
            }
        }

        gameEntity.setGameState(ENDED);
        repository.save(gameEntity);

        return gameEntity.toDTO();
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

    private void increaseGameCount(TournamentEntity tournament, TeamEntity team, GroupEntity group) {
        TeamStatisticsEntityPK teamPK = new TeamStatisticsEntityPK(tournament, team);
        teamStatisticsService.incrementGameCount(teamPK);
        groupInfoService.incrementGameCount(group, team);

        team.getPlayers()
                .forEach(player -> increaseGamePlayed(tournament, player));
    }

    private void increaseGamePlayed(TournamentEntity tournament, PlayerEntity player) {
        PlayerStatisticsEntityPK playerPk = new PlayerStatisticsEntityPK(tournament, player);
        playerStatisticsService.incrementGamePlayed(playerPk);
    }

    private void updatePointsAndStatistic(int foundTeam, int rivalTeam, TeamStatisticsEntity teamStatistics, GroupInfoEntity groupInfoEntity) {

        if (foundTeam > rivalTeam) {
            groupInfoEntity.setWinCount(groupInfoEntity.getWinCount() + 1);
            groupInfoEntity.setPoints(teamStatistics.getPoints() + 3);

            teamStatistics.setWinCount(teamStatistics.getWinCount() + 1);
            teamStatistics.setPoints(teamStatistics.getPoints() + 3);
        } else if (foundTeam == rivalTeam) {
            groupInfoEntity.setWinCount(groupInfoEntity.getWinCount() + 1);
            groupInfoEntity.setPoints(teamStatistics.getPoints() + 1);

            teamStatistics.setDrawCount(teamStatistics.getDrawCount() + 1);
            teamStatistics.setPoints(teamStatistics.getPoints() + 1);
        } else {
            groupInfoEntity.setLoseCount(teamStatistics.getLoseCount() + 1);
            teamStatistics.setLoseCount(teamStatistics.getLoseCount() + 1);
        }

    }

    private List<NewGameDTO> findGameByGroup(List<GroupEntity> allGroups, List<GameEntity> allGameByDate) {
        List<NewGameDTO> returnGameByDate = new ArrayList<>();
        for (GroupEntity group : allGroups) {
            NewGameDTO newGame = new NewGameDTO();
            newGame.setTournamentName(group.getTournament().getTournamentName());
            newGame.setTournamentLogo(group.getTournament().getTournamentLogo());
            newGame.setGroupName(group.getGroupName());
            newGame.setGroupId(group.getGroupId());
            newGame.setTournamentId(group.getTournament().getTournamentId());

            List<GameDTO> games = new ArrayList<>();
            for (GameEntity game : allGameByDate) {
                if (group.equals(game.getGroup())) {
                    games.add(game.toDTO());
                }
            }
            newGame.setGames(games);

            if (!games.isEmpty()) {
                returnGameByDate.add(newGame);
            }
        }
        return returnGameByDate;
    }
}
