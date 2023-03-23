package com.example.livescore.service.group.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
