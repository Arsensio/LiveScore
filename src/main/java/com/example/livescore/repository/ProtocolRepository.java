package com.example.livescore.repository;

import com.example.livescore.models.ProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends JpaRepository<ProtocolEntity,Long> {
}
