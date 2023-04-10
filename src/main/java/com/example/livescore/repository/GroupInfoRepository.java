package com.example.livescore.repository;

import com.example.livescore.models.GroupInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GroupInfoRepository extends JpaRepository<GroupInfoEntity,Long> {

    @Modifying
    @Query("update GroupInfoEntity g set g.gamePlayed = g.gamePlayed + 1 where g.id = ?1 AND g.team.teamId = ?2")
    void incrementGameCount(Long groupId, Long teamId);
}
