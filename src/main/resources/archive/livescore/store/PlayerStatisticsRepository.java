package com.example.livescore.store;

import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity,Long>{

    PlayerStatisticsEntity findPlayerStatisticsEntitiesById(PlayerStatisticsEntityPK id);

    @Query(value = "SELECT * FROM player_statistics WHERE group_id =?1 ORDER BY goals DESC", nativeQuery = true)
    List<PlayerStatisticsEntity> getPlayerStatisticsEntitiesByGroupIdAndGoals(Long groupId);

    @Query(value = "SELECT * FROM player_statistics WHERE group_id =?1 ORDER BY assists DESC", nativeQuery = true)
    List<PlayerStatisticsEntity> getPlayerStatisticsEntitiesByGroupIdAndAssists(Long groupId);

}
