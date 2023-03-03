package com.example.livescore.store;

import com.example.livescore.models.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TournamentRepository extends JpaRepository<TournamentEntity,Long>{
}
