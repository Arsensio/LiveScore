package com.example.livescore.repository;

import com.example.livescore.models.EventEntity;
import com.example.livescore.models.EventInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInfoRepository extends JpaRepository<EventInfoEntity, Long> {

    List<EventInfoEntity> findAllByEvent(EventEntity event);

    @Query("FROM EventInfoEntity e WHERE e.player.playerId = ?1 AND e.event.protocol.protocolId = ?2")
    List<EventInfoEntity> findAllEventsByPlayerAndProtocol(Long playerId, Long protocolId);
}
