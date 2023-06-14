package com.example.livescore.service.tournament.impl;

import com.example.core.exception.exceptions.IllegalCupFormatException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.models.UserEntity;
import com.example.livescore.repository.TournamentRepository;
import com.example.livescore.repository.UserRepository;
import com.example.livescore.security.JwtService;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.livescore.enums.StatusEnum.FINISHED;
import static com.example.livescore.enums.StatusEnum.IN_PROGRESS;

@Service
public class DefaultTournamentService
        extends AbstractFootballService<TournamentEntity, TournamentDTO, SaveTournamentDTO, Long, TournamentRepository>
        implements TournamentService {

    private final GroupService groupService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public DefaultTournamentService(TournamentRepository repository, GroupService groupService, JwtService jwtService, UserRepository userRepository) {
        super(repository);
        this.groupService = groupService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    @Deprecated
    public TournamentDTO save(SaveTournamentDTO dto) {
        return this.saveEntity(dto,null).toDTO();
    }

    @Override
    public TournamentDTO update(Long id, SaveTournamentDTO dto) {
        TournamentEntity tournamentEntity = findEntityById(id);
        tournamentEntity.setTournamentName(dto.getTournamentName());
        tournamentEntity.setTournamentLogo(dto.getTournamentLogo());

        return repository.saveAndFlush(tournamentEntity).toDTO();
    }

    @Override
    public List<TournamentDTO> findAllByUserId(String token) {
        return repository.findAllTournamentByUserId(jwtService.extractUserId(token))
                .stream()
                .map(TournamentEntity::toDTO)
                .toList();
    }

    @Override
    public List<TournamentEntity> findAllEntity() {
        return repository.findAll();
    }

    @Override
    public List<Long> findAllTournamentId() {
        return repository.findAll()
                .stream()
                .map(TournamentEntity::getTournamentId)
                .toList();
    }

    @Override
    public TournamentDTO createLeague(SaveTournamentDTO saveTournamentDTO, String token) {
        TournamentEntity savedTournament = saveEntity(saveTournamentDTO,token);
        groupService.createGroupBYTournament(savedTournament, saveTournamentDTO.getLocation(), 0);
        return savedTournament.toDTO();
    }

    @Override
    public TournamentDTO createCup(SaveCupTournamentDTO dto, String token) {
        Integer teamsNum = dto.getTeamsNum();

        if (teamsNum % 2 != 0) {
            throw IllegalCupFormatException.build(teamsNum);
        }

        TournamentEntity savedTournament = repository.save(getDefaultTournamentEntity(dto,token));

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
    public List<TournamentDTO> findAllCupTournamentByUser(String token) {
        return repository.findAllCupByUserId(jwtService.extractUserId(token))
                .stream()
                .map(TournamentEntity::toDTO)
                .toList();
    }

    @Override
    public TournamentDTO finishTournament(TournamentEntity tournament) {
        tournament.setTournamentStatus(FINISHED.toString());
        return repository.saveAndFlush(tournament).toDTO();
    }

    private TournamentEntity saveEntity(SaveTournamentDTO dto, String token) {
        return repository.save(getDefaultTournamentEntity(dto, token));
    }

    private <D extends SaveTournamentDTO> TournamentEntity getDefaultTournamentEntity(D dto, String token) {
        return new TournamentEntity(
                null,
                dto.getTournamentName(),
                dto.getTournamentType(),
                dto.getLocation(),
                dto.getTournamentLogo(),
                dto.getTeamsNum(),
                IN_PROGRESS.toString(),
                userRepository.findUserEntitiesByUserId(jwtService.extractUserId(token))
        );
    }
}
