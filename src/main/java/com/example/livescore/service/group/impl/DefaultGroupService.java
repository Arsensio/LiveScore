package com.example.livescore.service.group.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultGroupService
        extends AbstractFootballService<GroupEntity, GroupDTO, SaveGroupDTO, Long, GroupRepository>
        implements GroupService {

    private final TournamentService tournamentService;

    public DefaultGroupService(GroupRepository repository, TournamentService tournamentService) {
        super(repository);
        this.tournamentService = tournamentService;
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
    public GroupDTO save(SaveGroupDTO dto) {
        return repository.save(new GroupEntity(
                        null,
                        tournamentService.findEntityById(dto.getTournamentId()),
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
}
