package com.example.livescore.controllers;

import com.example.livescore.service.PlayerStatisticsServiceImpl;
import com.example.livescore.web.playerStatistics.InitPlayerStatisticDTO;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.playerStatistics.SavePlayerStatisticsDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/playerStatistics")
public class PlayerStatisticsController {
    private final PlayerStatisticsServiceImpl playerStatisticsService;


    @GetMapping("/goals/{id}")
    public List<PlayerStatisticsDTO> getAllByGoal(@PathVariable Long id) throws Throwable {
        return playerStatisticsService.getAllByGoal(id);
    }
    @GetMapping("/assists/{id}")
    public List<PlayerStatisticsDTO> getAllByAssists(@PathVariable Long id) throws Throwable {
        return playerStatisticsService.getAllByAssists(id);
    }

    @GetMapping
    public PlayerStatisticsDTO findOne(@RequestParam("group") Long group,@RequestParam("player") Long player) {

        return playerStatisticsService.getIndividual(group,player);
    }

    @PostMapping
    public PlayerStatisticsDTO save(@RequestBody InitPlayerStatisticDTO initPlayerStatisticDTO) {
        return playerStatisticsService.postIndividual(initPlayerStatisticDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playerStatisticsService.deleteIndividual(id);
    }

    @PutMapping()
    public PlayerStatisticsDTO update(@RequestParam("group") Long group,@RequestParam("player") Long player, @RequestBody SavePlayerStatisticsDTO playerStatisticsDTO) {
        return playerStatisticsService.putIndividual(group,player, playerStatisticsDTO);
    }

}
