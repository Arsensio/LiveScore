package com.example.livescore.service.tournament;

import com.example.core.service.FootballService;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;

public interface TournamentService extends FootballService<TournamentDTO, SaveTournamentDTO, Long> {
}
