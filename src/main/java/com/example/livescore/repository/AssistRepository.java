package com.example.livescore.repository;

import com.example.livescore.models.AssistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistRepository extends JpaRepository<AssistEntity, Long> {
}
