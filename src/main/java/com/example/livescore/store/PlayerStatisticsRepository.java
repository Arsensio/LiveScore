package com.example.livescore.store;

import com.example.livescore.models.PlayerStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity,Long>{
}
