package com.example.livescore.web.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTeamDTO {

    private String teamName;
    private String teamLogo;
    private long groupId;
}
