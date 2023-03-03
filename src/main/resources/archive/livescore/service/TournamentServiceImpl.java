package com.example.livescore.service;


import com.example.livescore.models.TournamentEntity;
import com.example.livescore.store.TournamentRepository;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import com.example.livescore.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements MainService<SaveTournamentDTO, TournamentDTO> {

    private final TournamentRepository tournamentRepository;

    @Override
    public List<TournamentDTO> getAll() {
        return tournamentRepository.findAll().stream().map(TournamentEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public TournamentDTO getIndividual(Long id) {
        return tournamentRepository.getReferenceById(id).toDTO();
    }

    @Override
    public TournamentDTO postIndividual(SaveTournamentDTO saveTournamentDTO) {

        TournamentEntity saved = new TournamentEntity(
                null,
                saveTournamentDTO.getTournamentName(),
                saveTournamentDTO.getTournamentType()
        );
        return tournamentRepository.save(saved).toDTO();
    }

    @Override
    public TournamentDTO putIndividual(Long id, SaveTournamentDTO saveTournamentDTO) {
        tournamentRepository.findById(id).ifPresentOrElse(tournamentEntity -> {
            tournamentEntity.setTournamentName(saveTournamentDTO.getTournamentName());
            tournamentEntity.setTournamentType(saveTournamentDTO.getTournamentType());
            tournamentRepository.saveAndFlush(tournamentEntity);
        }, () -> {
            throw new ResourceNotFoundException("There is no such Tournament");
        });

        return tournamentRepository.findById(id).get().toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        tournamentRepository.deleteById(id);
    }
}
