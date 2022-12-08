package com.example.livescore.service;


import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.store.TeamRepository;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements MainService<SaveTeamDTO, TeamDTO> {

    private final TeamRepository teamRepository;

    @Override
    public List<TeamDTO> getAll() {
        return teamRepository.findAll().stream().map(TeamEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeamDTO getIndividual(Long id) {
        return teamRepository.getReferenceById(id).toDTO();
    }

    @Override
    public TeamDTO postIndividual(SaveTeamDTO saveTeamDTO) {
        TeamEntity saved = teamRepository.save(new TeamEntity(
                        null,
                        saveTeamDTO.getTeamName(),
                        saveTeamDTO.getTeamLogo(),
                        null
                )
        );

        return saved.toDTO();
    }

    @Override
    public TeamDTO putIndividual(Long id, SaveTeamDTO saveTeamDTO) {
        teamRepository.findById(id).ifPresentOrElse(team -> {
            team.setTeamName(saveTeamDTO.getTeamName());
            team.setTeamLogo(saveTeamDTO.getTeamLogo());
            teamRepository.saveAndFlush(team);
        }, () -> {
            throw new ResourceNotFoundException("There is no such Team");
        });
        return teamRepository.findById(id).get().toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        teamRepository.deleteById(id);
    }
}
