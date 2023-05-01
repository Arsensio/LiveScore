package com.example.livescore.web.group_info;

import com.example.livescore.web.teams.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AfterDrawDTO {

    Long groupId;
    String groupName;
    List<TeamDTO> teams;
}
