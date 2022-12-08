package com.example.livescore.store;

import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TeamStatisticsRepository extends JpaRepository<TeamStatisticsEntity,Long>{

//    public List<TeamStatisticsEntity> getTeamStatisticsEntityByGroup(GroupEntity group);

    @Query(value = "SELECT * FROM team_statistics WHERE group_id =?1 ORDER BY points DESC", nativeQuery = true)
    public List<TeamStatisticsEntity>getTeamStatisticsEntityByGroupId(Long groupId);

    public TeamStatisticsEntity findTeamStatisticsEntityById(TeamStatisticsEntityPK teamStatisticsEntity);
}
