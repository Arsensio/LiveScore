package com.example.livescore2.service.team.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore2.exceptions.ResourceNotFoundException;
import com.example.livescore2.models.TeamEntity;
import com.example.livescore2.repository.TeamRepository;
import com.example.livescore2.service.team.TeamFootballService;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultTeamFootballService extends AbstractFootballService<
        TeamEntity, TeamDTO, SaveTeamDTO, Long, TeamRepository>
        implements TeamFootballService {

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
            throw new ResourceNotFoundException("There is no such Team");
        });
        return repository.findById(id).get().toDTO();
    }
}
