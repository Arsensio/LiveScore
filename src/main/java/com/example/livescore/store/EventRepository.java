package com.example.livescore.store;

import com.example.livescore.models.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<EventEntity,Long>{
}
