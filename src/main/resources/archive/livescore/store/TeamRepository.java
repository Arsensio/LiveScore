package com.example.livescore.store;

import com.example.livescore.models.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<TeamEntity,Long>{
}
