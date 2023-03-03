package com.example.livescore.controllers.player.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.player.PlayerController;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class DefaultPlayerController extends AbstractFootballController<PlayerService, PlayerDTO, SavePlayerDTO, Long>
        implements PlayerController {

    public DefaultPlayerController(PlayerService service) {
        super(service);
    }
}
