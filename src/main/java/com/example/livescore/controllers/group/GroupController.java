package com.example.livescore.controllers.group;

import com.example.core.controller.FootballController;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;

public interface GroupController extends FootballController<GroupDTO, SaveGroupDTO,Long> {
}
