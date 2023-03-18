package com.example.livescore.service.group;

import com.example.core.service.FootballService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;

public interface GroupService extends FootballService<GroupDTO, SaveGroupDTO, Long> {
}
