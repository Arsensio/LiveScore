package com.example.livescore.repository;

import com.example.livescore.models.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {

    @Query("FROM TournamentEntity")
    List<TournamentEntity> findAllTournamentByUserId(long userId);
}
