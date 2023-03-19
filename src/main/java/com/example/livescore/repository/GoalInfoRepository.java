package com.example.livescore.repository;

import com.example.livescore.models.GoalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalInfoRepository extends JpaRepository<GoalInfoEntity, Long> {
}
