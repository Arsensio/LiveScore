package com.example.livescore.service.group.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.PlayOffEnum;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.livescore.enums.PlayOffEnum.*;
import static com.example.livescore.enums.StatusEnum.IN_PROGRESS;

@Service
public class DefaultGroupService
        extends AbstractFootballService<GroupEntity, GroupDTO, SaveGroupDTO, Long, GroupRepository>
        implements GroupService {

    private final GroupInfoRepository groupInfoRepository;

    public DefaultGroupService(GroupRepository repository, GroupInfoRepository groupInfoRepository) {
        super(repository);
        this.groupInfoRepository = groupInfoRepository;
    }

    @Override
    public List<GroupDTO> findAllByTournamentId(long tournamentId) {
        return repository.findAllByTournamentId(tournamentId)
                .stream()
                .map(GroupEntity::toDTO)
                .toList();
    }

    @Override
    public List<GroupEntity> findAllEntity() {
        return repository.findAll();
    }

    @Override
    @Deprecated
    public GroupDTO save(SaveGroupDTO dto) {
        return repository.save(new GroupEntity(
                        null,
                        null,
                        dto.getGroupName(),
                        0,
                        null,
                        dto.isPlayoff()
                )
        ).toDTO();
    }

    @Override
    public GroupEntity findNextStage(GroupEntity group) {
        return repository.findNextStage(group.getTournament().getTournamentId(), group.getGroupOrder() + 1);
    }

    @Override
    public List<GroupEntity> createGroupsByTournament(TournamentEntity tournament, Integer teamNum) {
        int groupNum = teamNum / 4;

        List<GroupEntity> createdGroups = new ArrayList<>(IntStream.range(0, Math.min(groupNum, 26))
                .mapToObj(i -> {
                    char letter = (char) ('A' + i);
                    return new GroupEntity(
                            null,
                            tournament,
                            "Group " + letter,
                            0,
                            null,
                            false
                    );
                })
                .map(repository::save)
                .toList());

        createdGroups.addAll(createPlayOfGroups(tournament, groupNum * 2));

        return createdGroups;
    }

    @Override
    public List<GroupEntity> createPlayOfGroupsByTournament(TournamentEntity tournament, Integer teamsNum) {
        return createPlayOfGroups(tournament, teamsNum);
    }

    @Override
    public GroupEntity createGroupBYTournament(TournamentEntity tournament, String leagueNameOrLocation, Integer order) {
        return repository.save(new GroupEntity(
                        null,
                        tournament,
                        leagueNameOrLocation,
                        order,
                        null,
                        false
                )
        );
    }

    @Override
    public List<GroupEntity> findAllGroupInGroupStageByTournamentId(Long tournamentId) {
        return repository.findAllByTournamentIdAndPlayoffFalse(tournamentId);
    }

    @Override
    public List<GroupEntity> findAllByTournamentID(Long tournamentId) {
        return repository.findAllByTournamentId(tournamentId);
    }

    @Override
    public List<GroupDTO> findAllGroupStageByTournamentId(long tournamentId) {
        return repository.findAllGroupStageByTournamentId(tournamentId).stream().map(GroupEntity::toDTO).toList();
    }

    @Override
    public List<GroupDTO> findGroupTabsByTournament(long tournamentId) {
        List<GroupEntity> allByTournamentId = repository.findAllByTournamentId(tournamentId);
        boolean[] isGroupStageCreated = {false};
        boolean was = false;

        List<GroupDTO> groupTabs = new ArrayList<>();
        for (GroupEntity g : allByTournamentId) {
            List<GroupInfoEntity> byGroup = groupInfoRepository.findByGroup(g.getGroupId());
            GroupInfoEntity groupInfo = null;
            if (!byGroup.isEmpty()) {
                groupInfo = byGroup.get(0);
            }
            if (groupInfo != null && groupInfo.getStatus().equals(IN_PROGRESS.toString())) {
                if (!g.isPlayoff() && !was) {
                    was = true;
                    GroupDTO tab = new GroupDTO(null, g.getTournament().getTournamentName(), "Group Stage", false);
                    tab.setCurrentStage(true);
                    groupTabs.add(tab);
                } else if (g.isPlayoff()) {
                    GroupDTO tab = g.toDTO();
                    tab.setCurrentStage(true);
                    groupTabs.add(tab);
                }
            } else {
                if (!g.isPlayoff() && !was) {
                    was = true;
                    GroupDTO tab = new GroupDTO(null, g.getTournament().getTournamentName(), "Group Stage", false);
                    groupTabs.add(tab);
                } else if (g.isPlayoff()) {
                    GroupDTO tab = g.toDTO();
                    groupTabs.add(tab);
                }
            }
        }
        return groupTabs;

//        return allByTournamentId.stream()
//                .flatMap(g -> {
//                            GroupInfoEntity groupInfo = groupInfoRepository.findByGroup(g.getGroupId()).get(0);
//                            if (groupInfo.getStatus().equals(IN_PROGRESS.toString())) {
//                                if (!g.isPlayoff() && !isGroupStageCreated[0]) {
//                                    isGroupStageCreated[0] = true;
//                                    GroupDTO group_stage = new GroupDTO(null, g.getTournament().getTournamentName(), "Group Stage", false);
//                                    group_stage.setCurrentStage(true);
//                                    return Stream.of(group_stage);
//                                } else if (g.isPlayoff()) {
//                                    GroupDTO t = g.toDTO();
//                                    t.setCurrentStage(true);
//                                    return Stream.of(t);
//                                } else {
//                                    return Stream.empty();
//                                }
//                            } else {
//                                if (!g.isPlayoff() && !isGroupStageCreated[0]) {
//                                    isGroupStageCreated[0] = true;
//                                    GroupDTO group_stage = new GroupDTO(null, g.getTournament().getTournamentName(), "Group Stage", false);
//                                    return Stream.of(group_stage);
//                                } else if (g.isPlayoff()) {
//                                    GroupDTO t = g.toDTO();
//                                    return Stream.of(t);
//                                } else {
//                                    return Stream.empty();
//                                }
//                            }
//                        }
//                )
//                .toList();
    }

    @Override
    public GroupEntity findByGroupIdAndTournamentId(long tournament, long group) {
        return repository.findByGroupIdAndTournamentId(tournament, group);
    }

    private List<GroupEntity> createPlayOfGroups(TournamentEntity tournament, Integer teamNum) {
        List<GroupEntity> playOfGroups = new ArrayList<>();
        if (teamNum == 16) {
            playOfGroups.add(createPlayOf(tournament, ROUND_OF_16, 1));
            playOfGroups.add(createPlayOf(tournament, QUARTER_FINAL, 2));
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL, 3));
            playOfGroups.add(createPlayOf(tournament, FINAL, 4));
        } else if (teamNum == 8) {
            playOfGroups.add(createPlayOf(tournament, QUARTER_FINAL, 1));
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL, 2));
            playOfGroups.add(createPlayOf(tournament, FINAL, 3));
        } else if (teamNum == 4) {
            playOfGroups.add(createPlayOf(tournament, SEMI_FINAL, 1));
            playOfGroups.add(createPlayOf(tournament, FINAL, 2));
        } else if (teamNum == 2) {
            playOfGroups.add(createPlayOf(tournament, FINAL, 1));
        }
        return playOfGroups;
    }

    private GroupEntity createPlayOf(TournamentEntity tournament, PlayOffEnum playOffEnum, Integer order) {
        return repository.save(new GroupEntity(
                        null,
                        tournament,
                        playOffEnum.toString(),
                        order,
                        null,
                        true
                )
        );
    }
}
