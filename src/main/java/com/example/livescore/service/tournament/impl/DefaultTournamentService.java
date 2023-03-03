package com.example.livescore.service.tournament.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.TournamentRepository;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultTournamentService extends AbstractFootballService<TournamentEntity,
        TournamentDTO, SaveTournamentDTO, Long, TournamentRepository>
        implements TournamentService {

    public DefaultTournamentService(TournamentRepository repository) {
        super(repository);
    }

    @Override
    public TournamentDTO save(SaveTournamentDTO dto) {
        return repository.save(new TournamentEntity(
                null,
                dto.getTournamentName(),
                dto.getTournamentType()
        )).toDTO();
    }

    @Override
    public TournamentDTO update(Long id, SaveTournamentDTO dto) {
        repository.findById(id).ifPresentOrElse(tournamentEntity -> {
            tournamentEntity.setTournamentName(dto.getTournamentName());
            tournamentEntity.setTournamentType(dto.getTournamentType());
            repository.saveAndFlush(tournamentEntity);
        }, () -> {
            throw new ResourceNotFoundException("There is no such Team");
        });
        return repository.findById(id).get().toDTO();
    }
}
