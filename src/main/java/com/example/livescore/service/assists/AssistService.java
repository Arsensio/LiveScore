package com.example.livescore.service.assists;

import com.example.core.service.FootballService;
import com.example.livescore.models.AssistEntityPK;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

public interface AssistService extends FootballService<AssistDTO,SaveAssistsDTO,AssistEntityPK> {
}
