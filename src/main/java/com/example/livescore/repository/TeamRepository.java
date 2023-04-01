package com.example.livescore.repository;

import com.example.livescore.models.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    TeamEntity findTeamEntityByTeamName(String teamName);
}
