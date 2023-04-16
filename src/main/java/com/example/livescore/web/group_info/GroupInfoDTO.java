package com.example.livescore.web.group_info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoDTO {

    private String teamName;
    private String teamLogo;
    private Integer gamePlayed;
    private Integer winCount;
    private Integer drawCount;
    private Integer loseCount;
    private Integer goalCount;
    private Integer goalMissed;
    private Integer points;

}
