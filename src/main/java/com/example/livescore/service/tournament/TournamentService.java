package com.example.livescore.service.tournament;

import com.example.core.service.FootballService;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;

import java.util.List;

public interface TournamentService extends FootballService<TournamentDTO, SaveTournamentDTO, Long> {

    List<TournamentDTO> findAllByUserId(long userId);

    TournamentEntity findEntityById(long id);
}
