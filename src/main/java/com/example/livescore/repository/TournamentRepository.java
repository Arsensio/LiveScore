package com.example.livescore.repository;

import com.example.livescore.models.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {

    @Query("SELECT t FROM TournamentEntity t WHERE t.user.userId = :userId ORDER BY t.tournamentStatus DESC, t.tournamentName")
    List<TournamentEntity> findAllTournamentByUserId(long userId);

    @Query("SELECT t FROM TournamentEntity t WHERE t.user.userId = :userId AND t.tournamentStatus <> 'FINISHED' ORDER BY t.tournamentName")
    List<TournamentEntity> findAllNotFinishedByUserId(long userId);

    @Query("FROM TournamentEntity t WHERE (lower(t.tournamentName) like lower(concat('%',?1,'%'))) AND t.tournamentStatus <>'FINISHED'")
    List<TournamentEntity> searchByName(String name);

    @Query("FROM TournamentEntity t WHERE t.tournamentType ='CUP' AND t.user.userId = ?1 AND t.tournamentStatus <>'FINISHED' ORDER BY t.tournamentName")
    List<TournamentEntity> findAllCupByUserId(long userId);
}
