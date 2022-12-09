package com.example.livescore.store;

import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TeamStatisticsRepository extends JpaRepository<TeamStatisticsEntity, Long> {

//  List<TeamStatisticsEntity> getTeamStatisticsEntityByGroup(GroupEntity group);

    @Query(value = "SELECT * FROM team_statistics WHERE group_id =?1 ORDER BY points DESC", nativeQuery = true)
    List<TeamStatisticsEntity> getTeamStatisticsEntityByGroupId(Long groupId);

    TeamStatisticsEntity findTeamStatisticsEntityById(TeamStatisticsEntityPK teamStatisticsEntity);

    /**
     *      This method will be needed, when admin of tournament will create protocol manually, it will
     *      increment the game played count for both teams
     * */
    @Modifying
    @Query("update TeamStatisticsEntity t set t.gamePlayed = t.gamePlayed + 1 where t.id = ?1 and t.id = ?2")
    Integer incrementGameCount(TeamStatisticsEntityPK firstTeam, TeamStatisticsEntityPK secondTeam);

    /**
     *      Three methods below will increment win/lose/draw count for teams, will be invoked when game is finished
     * */
    @Modifying
    @Query("update TeamStatisticsEntity t set t.winCount = t.winCount + 1 where t.id = ?1")
    Integer incrementWinCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.loseCount = t.loseCount + 1 where t.id = ?1")
    Integer incrementLoseCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.drawCount = t.drawCount + 1 where t.id = ?1")
    Integer incrementDrawCount(TeamStatisticsEntityPK id);


    /**
     *      Those 2 methods below are for counting goals and missed goals
     * */
    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalCount = t.goalCount + 1 where t.id = ?1")
    Integer incrementGoalCount(TeamStatisticsEntityPK id);

    @Modifying
    @Query("update TeamStatisticsEntity t set t.goalMissed = t.goalMissed + 1 where t.id = ?1")
    Integer incrementGoalMissedCount(TeamStatisticsEntityPK id);


    // Спросить как высчитывается поинтс у Арсена
}
