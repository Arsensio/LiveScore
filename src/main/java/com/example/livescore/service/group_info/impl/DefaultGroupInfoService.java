package com.example.livescore.service.group_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


import static com.example.livescore.enums.GroupStatusEnum.CREATED;


@Service
public class DefaultGroupInfoService
        extends AbstractFootballService<GroupInfoEntity, GroupInfoDTO, SaveGroupInfoDTO, Long, GroupInfoRepository>
        implements GroupInfoService {

    private final TeamFootballService teamFootballService;
    private final GroupService groupService;

    public DefaultGroupInfoService(GroupInfoRepository repository, TeamFootballService teamFootballService, GroupService groupService) {
        super(repository);
        this.teamFootballService = teamFootballService;
        this.groupService = groupService;
    }

    @Override
    public GroupInfoEntity saveAfterDraw(GroupEntity group, TournamentEntity tournament, TeamEntity team) {
        return repository.save(new GroupInfoEntity(
                null,
                tournament.getTournamentLogo(),
                group.getGroupName(),
                team.getTeamName(),
                team.getTeamLogo(),
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                CREATED.toString(),
                group,
                team,
                tournament
        ));
    }

    @Override
    public GroupInfoEntity findEntityByGroupAndTeamId(GroupEntity group, TeamEntity team) {
        return repository.findEntityByGroupAndTeamId(group, team);
    }

    @Override
    public GroupInfoEntity saveAndFlash(GroupInfoEntity groupInfoEntity) {
        return repository.saveAndFlush(groupInfoEntity);
    }

    @Override
    public void incrementGameCount(GroupEntity group, TeamEntity team) {
        repository.incrementGameCount(group.getGroupId(), team.getTeamId());
    }

    @Override
    public List<GroupInfoDTO> createDrawInCup(List<SaveGroupInfoDTO> list) {
        List<GroupInfoDTO> returnList = new ArrayList<>();

        for (SaveGroupInfoDTO groupInfo : list) {
            TeamEntity team = teamFootballService.findEntityById(groupInfo.getTeamId());
            GroupEntity group = groupService.findEntityById(groupInfo.getGroupId());
            TournamentEntity tournament = group.getTournament();

            GroupInfoEntity groupInfoEntity = this.saveAfterDraw(group, tournament, team);
            returnList.add(groupInfoEntity.toDTO());
        }

        return returnList;
    }
}
