package com.example.livescore.store;

import com.example.livescore.models.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EventRepository extends JpaRepository<EventEntity, Long>{

    // если сможем надо переписать на jpql или hibernate авто. запрос
    @Query(value = "select * from events where protocol_id = ?1", nativeQuery = true)
    List<EventEntity> getEventEntitiesByProtocolId(Long protocolId);

}
