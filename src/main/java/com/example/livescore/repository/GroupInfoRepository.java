package com.example.livescore.repository;

import com.example.livescore.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupInfoRepository extends JpaRepository<GroupInfoEntity, Long> {

    @Modifying
    @Query("update GroupInfoEntity g set g.gamePlayed = g.gamePlayed + 1 where g.group.groupId = ?1 AND g.team.teamId = ?2")
    void incrementGameCount(Long groupId, Long teamId);

    @Modifying
    @Query("update GroupInfoEntity g set g.goalCount = g.goalCount + 1 where g.group.groupId = ?1 AND g.team.teamId = ?2")
    void incrementGoalCount(Long groupId, Long teamId);

    @Modifying
    @Query("update GroupInfoEntity g set g.goalMissed = g.goalMissed + 1 where g.group.groupId = ?1 AND g.team.teamId = ?2")
    void incrementGoalMissed(Long groupId, Long teamId);

    @Modifying
    @Query("update GroupInfoEntity g set g.goalCount = g.goalCount - 1 where g.group.groupId = ?1 AND g.team.teamId = ?2")
    void decrementGoalCount(Long groupId, Long teamId);

    @Modifying
    @Query("update GroupInfoEntity g set g.goalMissed = g.goalMissed -1 where g.group.groupId = ?1 AND g.team.teamId = ?2")
    void decrementGoalMissedCount(Long groupId, Long teamId);


    @Query("FROM GroupInfoEntity gi WHERE gi.group = ?1 AND gi.team= ?2 AND gi.tournament = ?3")
    GroupInfoEntity findEntityByTournamentAndGroupAndTeamId(GroupEntity group, TeamEntity team, TournamentEntity tournament);

    @Query(value = "FROM GroupInfoEntity gi WHERE gi.tournament.tournamentId = ?1 ORDER BY gi.points DESC")
    List<GroupInfoEntity> findAllByTournamentIdOrderByWinCount(Long tournament);

    @Query(value = "FROM GroupInfoEntity gi WHERE gi.tournament.tournamentId = ?1 AND gi.group.groupId= ?2 ORDER BY gi.points DESC")
    List<GroupInfoEntity> findAllByTournamentIdAndGroupIdOrderByWinCount(Long tournament, Long group);

}
