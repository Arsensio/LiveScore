package com.example.livescore2.service;

import com.example.core.service.AbstractService;
import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore2.models.TeamEntity;
import com.example.livescore2.repository.TeamRepository;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;

public class DefaultTeamService extends AbstractService<TeamEntity, TeamDTO, SaveTeamDTO, TeamRepository> implements TeamService {

    public DefaultTeamService(TeamRepository repository) {
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
    public TeamDTO update(long id, SaveTeamDTO saveTeamDTO) {
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
