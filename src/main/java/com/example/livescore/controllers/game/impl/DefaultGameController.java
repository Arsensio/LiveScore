package com.example.livescore.controllers.game.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.game.GameController;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class DefaultGameController
        extends AbstractFootballController<GameService, GameDTO, SaveGameDTO, Long>
        implements GameController {

    public DefaultGameController(GameService service) {
        super(service);
    }


    @GetMapping("/date")
    @Override
    public ResponseEntity<List<GameDTO>> findAllByDate(@RequestParam("date") String date) {
        return new ResponseEntity<>(service.findAllByDate(date), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GameDTO> save(@RequestBody SaveGameDTO saveGameDTO) {
        System.out.println("here");
        return new ResponseEntity<>(service.save(saveGameDTO), HttpStatus.OK);
    }
}
