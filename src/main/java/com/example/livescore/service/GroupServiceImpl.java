package com.example.livescore.service;

import com.example.livescore.models.GroupEntity;
import com.example.livescore.store.GroupRepository;
import com.example.livescore.store.TournamentRepository;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import com.example.livescore2.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements MainService<SaveGroupDTO, GroupDTO> {

    private final GroupRepository groupRepository;
    private final TournamentRepository tournamentRepository;

    @Override
    public List<GroupDTO> getAll() {
        return groupRepository.findAll().stream().map(GroupEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public GroupDTO getIndividual(Long id) {
        return groupRepository.getReferenceById(id).toDTO();
    }

    @Override
    public GroupDTO postIndividual(SaveGroupDTO saveGroupDTO) {
        return groupRepository.save(new GroupEntity(
                null,
                tournamentRepository.findById(saveGroupDTO.getTournamentId()).get(),
                saveGroupDTO.getGroupName(),
                null,
                saveGroupDTO.isPlayoff()
        )).toDTO();

    }

    @Override
    public GroupDTO putIndividual(Long id, SaveGroupDTO saveGroupDTO) {

        groupRepository.findById(id).ifPresentOrElse(groupEntity -> {
            groupEntity.setGroupName(saveGroupDTO.getGroupName());
            groupEntity.setPlayoff(saveGroupDTO.isPlayoff());
            groupRepository.saveAndFlush(groupEntity);
        },()->{
            throw new ResourceNotFoundException("There is no such Group");
        });

        return groupRepository.findById(id).get().toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        groupRepository.deleteById(id);
    }
}
