package com.example.livescore.service.game.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.*;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultGameService extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository>
        implements GameService {

    private final GroupRepository groupRepository;
    private final ProtocolRepository protocolRepository;
    private final TeamRepository teamRepository;
    private final TeamStatisticsRepository teamStatisticsRepository;

    public DefaultGameService(GameRepository repository, GroupRepository groupRepository, ProtocolRepository protocolRepository, TeamRepository teamRepository, TeamStatisticsRepository teamStatisticsRepository) {
        super(repository);
        this.groupRepository = groupRepository;
        this.protocolRepository = protocolRepository;
        this.teamRepository = teamRepository;
        this.teamStatisticsRepository = teamStatisticsRepository;
    }

    @Override
    public List<GameDTO> findAllByDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date1 = LocalDateTime.parse(date + " 00:00", df);
        LocalDateTime date2 = date1.plusMinutes(1439);

        return repository.findAllByGameDate(date1, date2)
                .stream()
                .map(GameEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameDTO startMatch(Long gameId) {
        Optional<GameEntity> foundGame = repository.findById(gameId);
        if (foundGame.isEmpty()) {
            throw ResourceNotFoundException.build(gameId, "GameEntity");
        } else {
            GameEntity gameEntity = foundGame.get();
            if (gameEntity.isPlayed()) {
                System.out.println("уже начался" + gameEntity.isPlayed());
                return gameEntity.toDTO();
            }
            GroupEntity group = gameEntity.getGroup();
            TeamEntity team1 = gameEntity.getProtocol().getTeam1();
            TeamEntity team2 = gameEntity.getProtocol().getTeam2();

            repository.updateIsPlayed(gameId);
            increaseGameCount(group, team1, team2);
            gameEntity.setPlayed(true);


            return gameEntity.toDTO();
        }
    }

    private void increaseGameCount(GroupEntity group, TeamEntity team1, TeamEntity team2) {
        TeamStatisticsEntityPK team1PK = new TeamStatisticsEntityPK(group, team1);
        TeamStatisticsEntityPK team2PK = new TeamStatisticsEntityPK(group, team2);
        System.out.println("update game count");
        teamStatisticsRepository.incrementGameCount(team1PK);
        teamStatisticsRepository.incrementGameCount(team2PK);
    }

    @Override
    public GameDTO save(SaveGameDTO dto) {
        GroupEntity group = getGroupById(dto.getGroupId());
        GameEntity createdGame = createGameEntity(group);
        ProtocolEntity defaultProtocol = createDefaultProtocol(createdGame, dto);

        createdGame.setProtocol(defaultProtocol);

        return createdGame.toDTO();
    }

    private GameEntity createGameEntity(GroupEntity group) {
        GameEntity gameEntity = new GameEntity(
                null,
                false,
                group,
                null
        );
        return repository.save(gameEntity);
    }

    private ProtocolEntity createDefaultProtocol(GameEntity savedEntity, SaveGameDTO dto) {
        ProtocolEntity protocol = new ProtocolEntity(
                null,
                savedEntity,
                getTeamById(dto.getTeam1Id()),
                getTeamById(dto.getTeam2Id()),
                dto.getDateTime(),
                0,
                0,
                null
        );
        return protocolRepository.save(protocol);
    }

    private GroupEntity getGroupById(long id) {
        Optional<GroupEntity> referenceById = groupRepository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, "GroupEntity");
        } else {
            return referenceById.get();
        }
    }

    private TeamEntity getTeamById(long id) {
        Optional<TeamEntity> referenceById = teamRepository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, "TeamEntity");
        } else {
            return referenceById.get();
        }
    }
}
