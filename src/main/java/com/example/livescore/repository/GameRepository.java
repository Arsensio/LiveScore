package com.example.livescore.repository;

import com.example.livescore.models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
//
//    @Query(value = "SELECT g.game_id,g.is_played,g.group_id FROM games g" +
//            "INNER JOIN protocols p on games.game_id = p.game_id\n" +
//            "WHERE p.date_time BETWEEN ?1 AND ?2", nativeQuery = true)
    @Query("FROM GameEntity g WHERE g.protocol.dateAndTime BETWEEN ?1 AND ?2 ORDER BY g.protocol.dateAndTime ")
    List<GameEntity> findAllByGameDate(LocalDateTime date1, LocalDateTime date2);
}
