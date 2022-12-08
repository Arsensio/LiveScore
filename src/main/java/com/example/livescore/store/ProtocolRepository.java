package com.example.livescore.store;

import com.example.livescore.models.ProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProtocolRepository extends JpaRepository<ProtocolEntity,Long>{
}
