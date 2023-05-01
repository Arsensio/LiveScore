package com.example.livescore.service.topic;

public interface TopicService {

    void createTopic(String topicName, Long tournamentId);

    String getTopicNameByProtocol(Long protocolId);

    String getTopicNameByTournament(Long tournamentId);
}
