package com.example.livescore.service.group.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.PlayOffEnum;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.livescore.enums.PlayOffEnum.*;

@Service
public class DefaultGroupService
        extends AbstractFootballService<GroupEntity, GroupDTO, SaveGroupDTO, Long, GroupRepository>
        implements GroupService {


    public DefaultGroupService(GroupRepository repository) {
        super(repository);
    }

    @Override
    public List<GroupDTO> findAllByTournamentId(long tournamentId) {

        return repository.findAllByTournamentId(tournamentId)
                .stream()
                .map(GroupEntity::toDTO)
                .toList();
    }

    @Override
    public List<GroupEntity> findAllEntity() {
        return repository.findAll();
    }

    @Override
    @Deprecated
    public GroupDTO save(SaveGroupDTO dto) {
        return repository.save(new GroupEntity(
                        null,
                        null,
                        dto.getGroupName(),
                        null,
                        dto.isPlayoff()
                )
        ).toDTO();
    }

    @Override
    public GroupEntity findEntityById(long id) {
        Optional<GroupEntity> referenceById = repository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, "GroupEntity");
        } else {
            return referenceById.get();
        }
    }

    @Override
    public List<GroupEntity> createGroupsByTournament(TournamentEntity tournament, Integer teamNum) {
        List<GroupEntity> createdGroups = new ArrayList<>();
        int groupNum = teamNum / 4;
        for (int i = 0; i < groupNum; i++) {
            if (i < 26) {
                char letter = (char) ('A' + i);
                GroupEntity savedGroup = repository.save(new GroupEntity(
                                null,
                                tournament,
                                "Group " + letter,
                                null,
                                false
                        )
                );
                createdGroups.add(savedGroup);
            } else {
                break;
            }
        }

        createdGroups.addAll(createPlayOfGroups(tournament, groupNum * 2));

        return createdGroups;
    }

    @Override
    public List<GroupEntity> createPlayOfGroupsByTournament(TournamentEntity tournament, Integer teamsNum) {
        return createPlayOfGroups(tournament, teamsNum);
    }


    @Override
    public GroupEntity createGroupBYTournament(TournamentEntity tournament, String leagueNameOrLocation) {
        return repository.save(new GroupEntity(
                        null,
                        tournament,
                        leagueNameOrLocation,
                        null,
                        false
                )
        );
    }

    private List<GroupEntity> createPlayOfGroups(TournamentEntity tournament, Integer teamNum) {
        List<GroupEntity> playOfGroups = new ArrayList<>();
        if (teamNum == 16) {
            playOfGroups.add(createPlayOf(tournament, ROUND_OF_16));
            playOfGroups.add(createPlayOf(tournament, QUARTER_FINAL));
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL));
            playOfGroups.add(createPlayOf(tournament, FINAL));
        } else if (teamNum == 8) {
            playOfGroups.add(createPlayOf(tournament, QUARTER_FINAL));
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL));
            playOfGroups.add(createPlayOf(tournament, FINAL));
        } else if (teamNum == 4) {
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL));
            playOfGroups.add(createPlayOf(tournament, FINAL));
        } else if (teamNum == 2) {
            playOfGroups.add(createPlayOf(tournament, FINAL));
        }
        return playOfGroups;
    }

    private GroupEntity createPlayOf(TournamentEntity tournament, PlayOffEnum playOffEnum) {
        return repository.save(new GroupEntity(
                        null,
                        tournament,
                        playOffEnum.toString(),
                        null,
                        true
                )
        );
    }
}
