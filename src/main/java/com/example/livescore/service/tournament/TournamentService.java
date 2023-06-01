package com.example.livescore.service.tournament;

import com.example.core.service.FootballService;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.web.tournaments.SaveCupTournamentDTO;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;

import java.util.List;

public interface TournamentService extends FootballService<TournamentEntity,TournamentDTO, SaveTournamentDTO, Long> {

    List<TournamentDTO> findAllByUserId(long userId);

    List<TournamentEntity> findAllEntity();

    TournamentDTO createLeague(SaveTournamentDTO saveTournamentDTO, String token);

    TournamentDTO createCup(SaveCupTournamentDTO saveCupTournamentDTO,String token);

    List<TournamentDTO> searchByName(String name);

    List<TournamentDTO> findAllCupTournamentByUser(long userId);

    TournamentDTO finishTournament(TournamentEntity tournament);
}
