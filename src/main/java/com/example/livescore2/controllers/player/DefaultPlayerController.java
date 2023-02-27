package com.example.livescore2.controllers.player;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore2.service.player.PlayerService;
import com.example.livescore2.web.players.PlayerDTO;
import com.example.livescore2.web.players.SavePlayerDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class DefaultPlayerController extends AbstractFootballController<PlayerService, PlayerDTO, SavePlayerDTO> implements PlayerController {

    public DefaultPlayerController(PlayerService service) {
        super(service);
    }
}
