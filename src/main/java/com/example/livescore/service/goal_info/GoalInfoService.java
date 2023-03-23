package com.example.livescore.service.goal_info;

import com.example.core.service.FootballService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

public interface GoalInfoService extends FootballService<AssistDTO, SaveAssistsDTO, Long> {
}
