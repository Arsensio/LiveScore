package com.example.livescore.controllers;


import com.example.livescore.service.TeamStatisticsServiceImpl;
import com.example.livescore.web.teamStatistics.InitTeamStatistics;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsPkDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
public class TeamStatisticsController {

    TeamStatisticsServiceImpl teamStatisticsService;

    @GetMapping
    public TeamStatisticsDTO findById(@RequestParam("group") Long group,@RequestParam("team") Long team){
        return teamStatisticsService.getIndividual(group,team);
    }

    @GetMapping("/{id}")
    public List<TeamStatisticsDTO> findAll(@PathVariable Long id){

        return teamStatisticsService.getAll(id);
    }

    @PutMapping("/{id}")
    public TeamStatisticsDTO update(@PathVariable Long id, @RequestBody SaveTeamStatisticsDTO teamStatisticsDTO){
        return teamStatisticsService.putIndividual(id,teamStatisticsDTO);
    }

    @PostMapping
    public TeamStatisticsDTO save(@RequestBody InitTeamStatistics initTeamStatistics){
        return teamStatisticsService.postIndividual(initTeamStatistics);
    }
}
