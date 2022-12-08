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

}
