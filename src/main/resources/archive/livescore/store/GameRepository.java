package com.example.livescore.store;

import com.example.livescore.models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GameRepository extends JpaRepository<GameEntity,Long>{

    List<GameEntity> getGameEntitiesByGroupGroupId(Long groupId);

}
