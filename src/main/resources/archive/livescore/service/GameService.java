package com.example.livescore.service;

import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;

import java.util.List;

public interface GameService {

    List<GameDTO> getAllGamesOfGroup(Long tournamentId);

    GameDTO getIndividual(Long id);

    GameDTO postIndividual(SaveGameDTO t);

    // GameDTO postIndividual(Map<SaveGameDTO, SaveProtocolDTO> saveGame);

    GameDTO putIndividual(Long id, SaveGameDTO t);

    void deleteIndividual(Long id);

}
