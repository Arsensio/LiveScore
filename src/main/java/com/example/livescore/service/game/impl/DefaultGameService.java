package com.example.livescore.service.game.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GameEntity;
import com.example.livescore.repository.GameRepository;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultGameService extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository> implements GameService {

    public DefaultGameService(GameRepository repository) {
        super(repository);
    }
}
