package com.example.livescore.repository;

import com.example.livescore.models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    @Query("FROM GameEntity g WHERE (g.protocol.dateAndTime BETWEEN ?1 AND ?2) AND (g.group.tournament.tournamentId IN ?3) ORDER BY g.protocol.dateAndTime ")
    List<GameEntity> findAllByGameDate(LocalDateTime date1, LocalDateTime date2, List<Long> tournaments);

    @Query("FROM GameEntity g WHERE (g.protocol.dateAndTime BETWEEN ?1 AND ?2) AND (g.group.tournament.user.userId IN ?3) ORDER BY g.protocol.dateAndTime ")
    List<GameEntity> findAllByGameDateAndUserId(LocalDateTime date1, LocalDateTime date2, Long userId);

    @Modifying
    @Query(value = "UPDATE GameEntity g set g.gameState = 'STARTED' where g.gameId =?1")
    Integer updateIsPlayed(long gameId);

    @Query("FROM GameEntity g where g.gameState = 'STARTED'")
    List<GameEntity> findAllLiveGame();
}
