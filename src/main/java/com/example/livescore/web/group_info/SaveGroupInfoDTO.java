package com.example.livescore.web.group_info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveGroupInfoDTO {

    private Long teamId;
    private Long groupId;
}
