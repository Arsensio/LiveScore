package com.example.livescore.service.group;

import com.example.core.service.FootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;

import java.util.List;

public interface GroupService extends FootballService<GroupEntity, GroupDTO, SaveGroupDTO, Long> {

    List<GroupDTO> findAllByTournamentId(long tournamentId);

    List<GroupEntity> findAllEntity();

    GroupEntity findNextStage(GroupEntity group);

    List<GroupEntity> createGroupsByTournament(TournamentEntity tournament, Integer groupNum);

    List<GroupEntity> createPlayOfGroupsByTournament(TournamentEntity tournament, Integer groupNum);

    GroupEntity createGroupBYTournament(TournamentEntity tournament, String leagueNameOrLocation, Integer order);

    List<GroupEntity> findAllGroupInGroupStageByTournamentId(Long tournamentId);

    List<GroupEntity> findAllByTournamentID(Long tournamentId);

    List<GroupDTO> findAllGroupStageByTournamentId(long tournamentId);

    List<GroupDTO> findGroupTabsByTournament(long tournamentId);

    GroupEntity findByGroupIdAndTournamentId(long tournament, long group);
}
