package com.example.livescore.repository;

import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.PlayerStatisticsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity, PlayerStatisticsEntityPK> {

    @Query(value = "SELECT * FROM player_statistics WHERE group_id = ?1 AND goals >= 1 ORDER BY goals DESC",nativeQuery = true)
    List<PlayerStatisticsEntity> findAllByGoals(long groupId);

    @Query(value = "SELECT * FROM player_statistics WHERE group_id = ?1 AND yellow_card >=1 ORDER BY yellow_card DESC",nativeQuery = true)
    List<PlayerStatisticsEntity> findAllByYellowCard(long groupId);

    @Query(value = "SELECT * FROM player_statistics WHERE group_id = ?1 AND red_card >=1 ORDER BY red_card DESC",nativeQuery = true)
    List<PlayerStatisticsEntity> findAllByRedCard(long groupId);

    @Query(value = "SELECT * FROM player_statistics WHERE group_id = ?1 AND assists >= 1 ORDER BY assists DESC",nativeQuery = true)
    List<PlayerStatisticsEntity> findAllByAssists(long groupId);
}
