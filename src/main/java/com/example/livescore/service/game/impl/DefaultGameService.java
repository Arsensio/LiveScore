package com.example.livescore.service.game.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GameEntity;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.repository.GameRepository;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.repository.ProtocolRepository;
import com.example.livescore.repository.TeamRepository;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultGameService extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository> implements GameService {

    private final GroupRepository groupRepository;
    private final ProtocolRepository protocolRepository;
    private final TeamRepository teamRepository;

    public DefaultGameService(GameRepository repository, GroupRepository groupRepository, ProtocolRepository protocolRepository, TeamRepository teamRepository) {
        super(repository);
        this.groupRepository = groupRepository;
        this.protocolRepository = protocolRepository;
        this.teamRepository = teamRepository;
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
