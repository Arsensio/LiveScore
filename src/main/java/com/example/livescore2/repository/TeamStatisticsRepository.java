package com.example.livescore2.repository;

import com.example.livescore2.models.TeamStatisticsEntity;
import com.example.livescore2.models.TeamStatisticsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStatisticsRepository extends JpaRepository<TeamStatisticsEntity, TeamStatisticsEntityPK> {
}
