package com.example.livescore.service.team.impl;

import com.example.core.service.AbstractFootballService;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.repository.TeamRepository;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultTeamFootballService extends AbstractFootballService<TeamEntity, TeamDTO, SaveTeamDTO, Long,
        TeamRepository> implements TeamFootballService {

    public DefaultTeamFootballService(TeamRepository repository) {
        super(repository);
    }

    @Override
    public TeamDTO save(SaveTeamDTO saveTeamDTO) {
        TeamEntity saved = repository.save(new TeamEntity(
                        null,
                        saveTeamDTO.getTeamName(),
                        saveTeamDTO.getTeamLogo(),
                        null
                )
        );

        return saved.toDTO();
    }

    @Override
    public TeamDTO update(Long id, SaveTeamDTO saveTeamDTO) {
        repository.findById(id).ifPresentOrElse(team -> {
            team.setTeamName(saveTeamDTO.getTeamName());
            team.setTeamLogo(saveTeamDTO.getTeamLogo());
            repository.saveAndFlush(team);
        }, () -> {
            throw ResourceNotFoundException.build(id, "Team");
        });
        return repository.findById(id).get().toDTO();
    }
}
