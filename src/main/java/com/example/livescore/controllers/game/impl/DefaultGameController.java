package com.example.livescore.controllers.game.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.game.GameController;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class DefaultGameController
        extends AbstractFootballController<GameService, GameDTO, SaveGameDTO,Long>
        implements GameController {

    public DefaultGameController(GameService service) {
        super(service);
    }
}
