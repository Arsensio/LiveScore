package com.example.livescore.repository;

import com.example.livescore.models.ProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolRepository extends JpaRepository<ProtocolEntity, Long> {

    @Query("FROM ProtocolEntity p where p.game.gameState = 'STARTED'")
    List<ProtocolEntity> findAllByGameStateStarted();
}
