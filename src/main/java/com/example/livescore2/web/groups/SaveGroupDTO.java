package com.example.livescore2.web.groups;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveGroupDTO {

    private Long groupId;
    private Long tournamentId;
    private String groupName;
    private boolean isPlayoff;

}
