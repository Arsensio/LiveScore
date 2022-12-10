package com.example.livescore.service;


import com.example.livescore.exceptions.ResourceNotFoundException;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.store.GroupRepository;
import com.example.livescore.store.TeamRepository;
import com.example.livescore.store.TeamStatisticsRepository;
import com.example.livescore.web.teamStatistics.InitTeamStatistics;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsPkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamStatisticsServiceImpl {

    private final TeamStatisticsRepository teamStatisticsRepository;
    private final GroupRepository groupRepository;
    private final TeamRepository teamRepository;

    public List<TeamStatisticsDTO> getAll(Long id) {
        return  teamStatisticsRepository.getTeamStatisticsEntityByGroupId(id).stream().map(TeamStatisticsEntity::toDTO).collect(Collectors.toList());
//        return teamStatisticsRepository.getTeamStatisticsEntityByGroup(groupRepository.findById(id).get()).stream().map(TeamStatisticsEntity::toDTO).collect(Collectors.toList());
    }

    public TeamStatisticsDTO getIndividual(Long groupId, Long teamId) {
        System.out.println(groupId +" "+ teamId);
        return teamStatisticsRepository.findTeamStatisticsEntityById(new TeamStatisticsEntityPK(groupRepository.findById(groupId).get(),teamRepository.findById(teamId).get())).toDTO();
    }

    public TeamStatisticsDTO postIndividual(InitTeamStatistics initTeamStatistics) {

        return teamStatisticsRepository.save(new TeamStatisticsEntity(
                new TeamStatisticsEntityPK(groupRepository.findById(initTeamStatistics.getGroup_id()).get(), teamRepository.findById(initTeamStatistics.getTeam_id()).get()),
                0,
                0,
                0,
                0,
                0,
                0,
                0
        )).toDTO();
    }

    public TeamStatisticsDTO putIndividual(Long groupId, Long teamId, SaveTeamStatisticsDTO saveTeamStatisticsDTO) {
        TeamStatisticsEntity teamStatisticsEntity = teamStatisticsRepository.findTeamStatisticsEntityById(new TeamStatisticsEntityPK(groupRepository.findById(groupId).get(),teamRepository.findById(teamId).get()));
        if (teamStatisticsEntity !=null){
            teamStatisticsEntity.setGamePlayed(saveTeamStatisticsDTO.getGamePlayed());
            teamStatisticsEntity.setWinCount(saveTeamStatisticsDTO.getWinCount());
            teamStatisticsEntity.setDrawCount(saveTeamStatisticsDTO.getDrawCount());
            teamStatisticsEntity.setLoseCount(saveTeamStatisticsDTO.getLoseCount());
            teamStatisticsEntity.setGoalCount(saveTeamStatisticsDTO.getGoalCount());
            teamStatisticsEntity.setGoalMissed(saveTeamStatisticsDTO.getGoalMissed());
            teamStatisticsEntity.setPoints(saveTeamStatisticsDTO.getPoints());
            teamStatisticsRepository.saveAndFlush(teamStatisticsEntity);
        }else {
            throw new ResourceNotFoundException("There is no such Team Id");
        }


        return teamStatisticsRepository.findTeamStatisticsEntityById(new TeamStatisticsEntityPK(groupRepository.findById(groupId).get(),teamRepository.findById(teamId).get())).toDTO();
    }

    public void deleteIndividual(Long id) {

    }
}
