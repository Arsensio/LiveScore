package com.example.livescore.repository;

import com.example.livescore.models.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

    @Query(value = "select tp.topic_name " +
            "from tournaments tr " +
            "join topics tp on tp.tournament_id = tr.tournament_id " +
            "join groups gr on gr.tournament_id = tr.tournament_id " +
            "join games gm on gm.group_id = gr.group_id " +
            "join protocols p on p.game_id = gm.game_id " +
            "where p.protocol_id = ?1", nativeQuery = true)
    String getTopicName(Long protocolId);
}
