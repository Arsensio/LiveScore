package com.example.livescore2.repository;

import com.example.livescore2.models.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity,Long> {
}
