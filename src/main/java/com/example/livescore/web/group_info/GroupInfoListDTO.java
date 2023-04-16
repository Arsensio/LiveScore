package com.example.livescore.web.group_info;

import com.example.livescore.models.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoListDTO {

    private String tournamentName;
    private String tournamentLogo;
    private String groupName;
    private Long groupId;
    List<GroupInfoDTO> sortedByPointTeams;

    public GroupInfoListDTO(GroupEntity group) {
        this.tournamentName = group.getTournament().getTournamentName();
        this.tournamentLogo = group.getTournament().getTournamentLogo();
        this.groupName = group.getGroupName();
        this.groupId = group.getGroupId();
    }
}
