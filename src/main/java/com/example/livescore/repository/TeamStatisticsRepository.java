package com.example.livescore.repository;

import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.web.teamStatistics.StatisticDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatisticsRepository extends JpaRepository<TeamStatisticsEntity, TeamStatisticsEntityPK> {

    @Query(value = "SELECT * FROM team_statistics WHERE group_id = ?1 AND goal_count >= 1 ORDER BY goal_count DESC", nativeQuery = true)
    List<TeamStatisticsEntity> findAllByGroupIdOrderByGoalCount(Long group);

    @Query(value = "SELECT * FROM team_statistics WHERE group_id = ?1 ORDER BY points DESC", nativeQuery = true)
    List<TeamStatisticsEntity> findAllByGroupIdOrderByWinCount(Long group);

    @Query(value = "SELECT new com.example.livescore.web.teamStatistics.StatisticDTO(ts.id.group.groupName,t.teamName,sum(ps.redCard),ts.gamePlayed)" +
            "FROM TeamEntity t inner join PlayerEntity p on t.teamId= p.team.teamId " +
            "inner join PlayerStatisticsEntity ps on ps.id.player.playerId = p.playerId " +
            "inner join TeamStatisticsEntity ts on ts.id.team.teamId = t.teamId " +
            "WHERE ps.id.group.groupId = ?1 AND ps.redCard >=1" +
            "group by t.teamName,ts.gamePlayed,ts.id.group.groupName " +
            "ORDER BY sum(ps.redCard) DESC")
    List<StatisticDTO> findAllByGroupIdOrderByRedCard(long group);

    @Query(value = "SELECT new com.example.livescore.web.teamStatistics.StatisticDTO(ts.id.group.groupName,t.teamName,sum(ps.yellowCard),ts.gamePlayed)" +
            "FROM TeamEntity t inner join PlayerEntity p on t.teamId= p.team.teamId " +
            "inner join PlayerStatisticsEntity ps on ps.id.player.playerId = p.playerId " +
            "inner join TeamStatisticsEntity ts on ts.id.team.teamId = t.teamId " +
            "WHERE ps.id.group.groupId = ?1 AND ps.yellowCard >=1" +
            "group by t.teamName,ts.gamePlayed,ts.id.group.groupName " +
            "ORDER BY sum(ps.yellowCard) DESC")
    List<StatisticDTO> findAllByGroupIdOrderByYellowCard(long group);

    @Query("SELECT new com.example.livescore.web.teams.TeamDTO(t.teamId, t.teamName, t.teamLogo)" +
            "FROM TeamEntity t inner join TeamStatisticsEntity ts on t.teamId = ts.id.team.teamId " +
            "inner join GroupEntity g on g.groupId = ts.id.group.groupId " +
            "WHERE g.groupId = ?1")
    List<TeamDTO> findAllTeamByGroupId(long groupId);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.gamePlayed = t.gamePlayed + 1 where t.id = ?1")
    Integer incrementGameCount(TeamStatisticsEntityPK firstTeam);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.points = t.points+3 where t.id =?1")
    Integer incrementPoint(TeamStatisticsEntityPK team);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.points = t.points+1 where t.id =?1")
    Integer drawPoint(TeamStatisticsEntityPK team);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.winCount = t.winCount + 1 where t.id = ?1")
    Integer incrementWinCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.loseCount = t.loseCount + 1 where t.id = ?1")
    Integer incrementLoseCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.drawCount = t.drawCount + 1 where t.id = ?1")
    Integer incrementDrawCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalCount = t.goalCount + 1 where t.id = ?1")
    Integer incrementGoalCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalCount = t.goalCount - 1 where t.id = ?1")
    Integer decrementGoalCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalMissed = t.goalMissed + 1 where t.id = ?1")
    Integer incrementGoalMissedCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalMissed = t.goalMissed - 1 where t.id = ?1")
    Integer decrementGoalMissedCount(TeamStatisticsEntityPK id);
}
