package com.example.livescore.store;

import com.example.livescore.models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<GameEntity,Long>{
}
