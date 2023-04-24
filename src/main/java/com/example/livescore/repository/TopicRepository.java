package com.example.livescore.repository;

import com.example.livescore.models.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

    @Query(value = "select t.topicName from TopicEntity t where t.tournament.tournamentId = ?1")
    String getTopicName(Long tournamentId);
}
