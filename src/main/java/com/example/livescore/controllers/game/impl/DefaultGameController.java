package com.example.livescore.controllers.game.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.game.GameController;
import com.example.livescore.security.JwtService;
import com.example.livescore.service.game.GameService;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.NewGameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/game")
public class DefaultGameController
        extends AbstractFootballController<GameService, GameDTO, SaveGameDTO, Long>
        implements GameController {

    private final JwtService jwtService;

    public DefaultGameController(GameService service, JwtService jwtService) {
        super(service);
        this.jwtService = jwtService;
    }


    @GetMapping("/date")
    @Override
    public ResponseEntity<List<GameDTO>> findAllByDate(@RequestParam("date") String date) {
        return new ResponseEntity<>(service.findAllByDate(date), OK);
    }

    @GetMapping("/new/date")
    @Override
    public ResponseEntity<List<NewGameDTO>> newFindAllByDate(@RequestParam("date") String date, @RequestParam("tournaments") List<Long> tournaments) {
        return new ResponseEntity<>(service.newFindAllByDate(date, tournaments), OK);
    }

    @Override
    @GetMapping("/admin/date")
    public ResponseEntity<List<NewGameDTO>> findAllAdminGameByDate(@RequestParam("date") String date, @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(service.findAllAdminGameByDate(date, token), OK);
    }

    //    @Override
    @GetMapping("/admin/resource")
    public ResponseEntity<String> adminResourceEndpoint(@RequestHeader("Authorization") String token) {

        return null;
    }

    @GetMapping("/live")
    @Override
    public ResponseEntity<List<NewGameDTO>> findAllLiveMatches() {
        return new ResponseEntity<>(service.findAllLiveMatches(), OK);
    }

    @PostMapping("/start/{id}")
    @Override
    public ResponseEntity<GameDTO> startMatch(@PathVariable Long id) {
        return new ResponseEntity<>(service.startMatch(id), OK);
    }

    @Override
    @PostMapping("/end/{id}")
    public ResponseEntity<GameDTO> endMatch(@PathVariable Long id) {
        return new ResponseEntity<>(service.endMatch(id), OK);
    }
}
