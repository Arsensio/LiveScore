package com.example.livescore.controllers;


import com.example.livescore.service.TournamentServiceImpl;
import com.example.livescore.web.tournaments.SaveTournamentDTO;
import com.example.livescore.web.tournaments.TournamentDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    TournamentServiceImpl tournamentService;

    @GetMapping
    public List<TournamentDTO> findAll(){
        return tournamentService.getAll();
    }

    @PostMapping
    public TournamentDTO save(@RequestBody SaveTournamentDTO saveTournamentDTO) {
        return tournamentService.postIndividual(saveTournamentDTO);
    }

    @GetMapping("/{id}")
    public TournamentDTO findById(@PathVariable Long id){
        return tournamentService.getIndividual(id);
    }

    @PutMapping("/{id}")
    public TournamentDTO update(@PathVariable Long id,@RequestBody SaveTournamentDTO saveTournamentDTO){
        return tournamentService.putIndividual(id,saveTournamentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        tournamentService.deleteIndividual(id);
    }
}
