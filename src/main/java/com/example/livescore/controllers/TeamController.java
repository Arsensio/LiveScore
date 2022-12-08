package com.example.livescore.controllers;


import com.example.livescore.service.TeamServiceImpl;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {

    private TeamServiceImpl teamService;

    @GetMapping("/{id}")
    public TeamDTO findOne(@PathVariable Long id) throws Throwable {
        return teamService.getIndividual(id);
    }


    @GetMapping
    public List<TeamDTO> findAll() {
        return teamService.getAll();
    }

    @PostMapping
    public TeamDTO save(@RequestBody SaveTeamDTO saveTeamDTO) {
        return teamService.postIndividual(saveTeamDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamService.deleteIndividual(id);
    }

    @PutMapping("/{id}")
    public TeamDTO update(@PathVariable Long id, @RequestBody SaveTeamDTO saveTeamDTO) {
        return teamService.putIndividual(id, saveTeamDTO);
    }


}
