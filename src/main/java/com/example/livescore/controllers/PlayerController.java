package com.example.livescore.controllers;

import com.example.livescore.service.PlayerServiceImpl;
import com.example.livescore.web.players.PlayerDTO;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
//@RequestMapping("/players")
public class PlayerController {

//    private final PlayerServiceImpl playerService;
//
//    @GetMapping("/{id}")
//    public PlayerDTO findOne(@PathVariable Long id) throws Throwable {
//        return playerService.getIndividual(id);
//    }
//
//    @GetMapping
//    public List<PlayerDTO> findAll() {
//        return playerService.getAll();
//    }
//
//    @PostMapping
//    public PlayerDTO save(@RequestBody SavePlayerDTO savePlayerDTO) {
//        return playerService.postIndividual(savePlayerDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        playerService.deleteIndividual(id);
//    }
//
//    @PutMapping("/{id}")
//    public PlayerDTO update(@PathVariable Long id, @RequestBody SavePlayerDTO savePlayerDTO) {
//        return playerService.putIndividual(id, savePlayerDTO);
//    }

}
