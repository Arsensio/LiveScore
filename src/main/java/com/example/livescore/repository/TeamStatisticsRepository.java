package com.example.livescore.repository;

import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatisticsRepository extends JpaRepository<TeamStatisticsEntity, TeamStatisticsEntityPK> {


    @Query(value = "SELECT * FROM team_statistics WHERE group_id = ?1 AND goal_count >= 1 ORDER BY goal_count DESC", nativeQuery = true)
    List<TeamStatisticsEntity> findAllByGroupIdOrderByGoalCount(Long group);

    @Query(value = "SELECT * FROM team_statistics WHERE group_id = ?1 ORDER BY points DESC", nativeQuery = true)
    List<TeamStatisticsEntity> findAllByGroupIdOrderByWinCount(Long group);
}
