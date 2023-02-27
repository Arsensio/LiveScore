package com.example.livescore2.service.team_statistics;

import com.example.core.service.AbstractFootballService;
import com.example.livescore2.models.TeamStatisticsEntity;
import com.example.livescore2.repository.TeamStatisticsRepository;
import com.example.livescore2.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore2.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultTeamStatisticsService extends
        AbstractFootballService<TeamStatisticsEntity, TeamStatisticsDTO, SaveTeamStatisticsDTO, TeamStatisticsRepository>
        implements TeamStatisticsService {

    public DefaultTeamStatisticsService(TeamStatisticsRepository repository) {
        super(repository);
    }
}
