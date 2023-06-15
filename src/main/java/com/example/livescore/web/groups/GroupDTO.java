package com.example.livescore.web.groups;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private Long groupId;
    private String tournamentName;
    private String groupName;
    private boolean isPlayoff;
    private boolean currentStage;

    public GroupDTO(Long groupId, String tournamentName, String groupName, boolean isPlayoff) {
        this.groupId = groupId;
        this.tournamentName = tournamentName;
        this.groupName = groupName;
        this.isPlayoff = isPlayoff;
    }
}
