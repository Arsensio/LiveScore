package com.example.livescore.store;

import com.example.livescore.models.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<PlayerEntity,Long>{
}
