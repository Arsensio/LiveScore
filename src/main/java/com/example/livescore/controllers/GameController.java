package com.example.livescore.controllers;


import com.example.livescore.service.GameServiceImpl;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameServiceImpl gameService;

    @GetMapping
    public List<GameDTO> getAllGamesOfGroup(@RequestParam("groupId") Long groupId) {
        return gameService.getAllGamesOfGroup(groupId);
    }

    @GetMapping("/{id}")
    public GameDTO getGame(@PathVariable Long id) {
        return gameService.getIndividual(id);
    }

    @PostMapping()
    public GameDTO postGame(@RequestBody SaveGameDTO saveGameDTO) {
        return gameService.postIndividual(saveGameDTO);
    }

    // надо будет как то передавать данные о протоколе, так как надо создавать протокол сразу после создания гейма
//    @PostMapping()
//    public GameDTO postGame(@RequestBody Map<SaveGameDTO, SaveProtocolDTO> saveGame) {
//        return gameService.postIndividual(saveGame);
//    }

    @PutMapping("/{id}")
    public GameDTO putGame(@PathVariable Long id, @RequestBody SaveGameDTO saveGameDTO) {
        return gameService.putIndividual(id, saveGameDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteIndividual(id);
    }
}

