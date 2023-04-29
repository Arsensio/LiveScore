package com.example.livescore.repository;

import com.example.livescore.models.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query(value = "SELECT * FROM groups where tournament_id = ?1 order by group_id", nativeQuery = true)
    List<GroupEntity> findAllByTournamentId(long tournamentId);

    @Query(value = "SELECT * FROM groups where tournament_id = ?1 AND is_playoff = false order by group_id", nativeQuery = true)
    List<GroupEntity> findAllGroupStageByTournamentId(long tournamentId);

    @Query(value = "FROM GroupEntity g where (g.tournament.tournamentId = ?1 AND g.isPlayoff = false AND g.tournament.tournamentType = 'CUP') OR (g.tournament.tournamentId = ?1 AND g.tournament.tournamentType = 'LEAGUE')")
    List<GroupEntity> findAllByTournamentIdAndPlayoffFalse(long tournamentId);

    @Query(value = "SELECT * FROM groups where tournament_id = ?1 AND group_id = ?2 order by group_id", nativeQuery = true)
    List<GroupEntity> findGroupByTournamentIdAndGroupId(long tournamentId, long group);

    @Query(value = "SELECT * FROM groups where tournament_id =?1 AND group_order = ?2", nativeQuery = true)
    GroupEntity findNextStage(Long tournament, Integer groupOrder);
}
