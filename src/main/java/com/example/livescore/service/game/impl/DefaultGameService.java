package com.example.livescore.service.game.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GameEntity;
import com.example.livescore.repository.GameRepository;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultGameService extends AbstractFootballService<GameEntity, GameDTO, SaveGameDTO, Long, GameRepository> implements GameService {

    public DefaultGameService(GameRepository repository) {
        super(repository);
    }

    @Override
    public List<GameDTO> findAllByDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date1 = LocalDateTime.parse(date + " 00:00", df);
        LocalDateTime date2 = date1.plusMinutes(1439);

        return repository.findAllByGameDate(date1, date2)
                .stream()
                .map(GameEntity::toDTO)
                .collect(Collectors.toList());
    }
}
