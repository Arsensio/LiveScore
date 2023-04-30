package com.example.livescore.service.tournament.impl;

import com.example.core.exception.exceptions.IllegalCupFormatException;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.TournamentRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.livescore.enums.StatusEnum.FINISHED;
import static com.example.livescore.enums.StatusEnum.IN_PROGRESS;

@Service
public class DefaultTournamentService
        extends AbstractFootballService<TournamentEntity, TournamentDTO, SaveTournamentDTO, Long, TournamentRepository>
        implements TournamentService {

    private final GroupService groupService;

    public DefaultTournamentService(TournamentRepository repository, GroupService groupService) {
        super(repository);
        this.groupService = groupService;
    }


    @Override
    public TournamentDTO save(SaveTournamentDTO dto) {
        return this.saveEntity(dto).toDTO();
    }

    @Override
    public TournamentDTO update(Long id, SaveTournamentDTO dto) {
        repository.findById(id).ifPresentOrElse(tournamentEntity -> {
            tournamentEntity.setTournamentName(dto.getTournamentName());
            tournamentEntity.setTournamentType(dto.getTournamentType());
            repository.saveAndFlush(tournamentEntity);
        }, () -> {
            throw ResourceNotFoundException.build(id, "TournamentEntity");
        });
        return repository.findById(id).get().toDTO();
    }

    @Override
    public List<TournamentDTO> findAllByUserId(long userId) {
        return repository.findAllTournamentByUserId(userId)
                .stream()
                .map(TournamentEntity::toDTO)
                .toList();
    }

    @Override
    public List<TournamentEntity> findAllEntity() {
        return repository.findAll();
    }

    @Override
    public TournamentEntity findEntityById(long id) {
        Optional<TournamentEntity> tournament = repository.findById(id);
        if (tournament.isEmpty()) {
            throw ResourceNotFoundException.build(id, "TournamentEntity");
        } else {
            return tournament.get();
        }
    }

    @Override
    public TournamentDTO createLeague(SaveTournamentDTO saveTournamentDTO) {
        TournamentEntity savedTournament = saveEntity(saveTournamentDTO);
        groupService.createGroupBYTournament(savedTournament, saveTournamentDTO.getLocation(), 0);
        return savedTournament.toDTO();
    }

    @Override
    public TournamentDTO createCup(SaveCupTournamentDTO dto) {
        Integer teamsNum = dto.getTeamsNum();

        if (teamsNum % 2 != 0) {
            throw IllegalCupFormatException.build(teamsNum);
        }

        TournamentEntity savedTournament = repository.save(new TournamentEntity(
                null,
                dto.getTournamentName(),
                dto.getTournamentType(),
                dto.getLocation(),
                dto.getTournamentLogo(),
                dto.getTeamsNum(),
                IN_PROGRESS.toString()
        ));

        if (dto.isPlayOf()) {
            groupService.createPlayOfGroupsByTournament(savedTournament, teamsNum);
        } else {
            groupService.createGroupsByTournament(savedTournament, teamsNum);
        }
        return savedTournament.toDTO();
    }

    @Override
    public List<TournamentDTO> searchByName(String name) {
        return repository.searchByName(name).stream().map(TournamentEntity::toDTO).toList();
    }

    @Override
    public List<TournamentDTO> findAllCupTournamentByUser(long userId) {
        return repository.findAllCupByUserId(userId).stream().map(TournamentEntity::toDTO).toList();
    }

    @Override
    public TournamentDTO finishTournament(TournamentEntity tournament) {
        tournament.setTournamentStatus(FINISHED.toString());
        return repository.saveAndFlush(tournament).toDTO();
    }

    private TournamentEntity saveEntity(SaveTournamentDTO dto) {
        return repository.save(new TournamentEntity(
                null,
                dto.getTournamentName(),
                dto.getTournamentType(),
                dto.getLocation(),
                dto.getTournamentLogo(),
                dto.getTeamsNum(),
                IN_PROGRESS.toString()
        ));
    }
}
