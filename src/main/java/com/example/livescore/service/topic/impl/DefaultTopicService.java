package com.example.livescore.service.topic.impl;

import com.example.livescore.models.TopicEntity;
import com.example.livescore.repository.TopicRepository;
import com.example.livescore.service.topic.TopicService;
import com.example.livescore.service.tournament.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTopicService implements TopicService {

    private final TopicRepository topicRepository;
    private final TournamentService tournamentService;

    @Override
    public void createTopic(String topicName, Long tournamentId) {
        topicRepository.save(new TopicEntity(
                null,
                topicName,
                tournamentService.findEntityById(tournamentId))
        );
    }

    @Override
    public String getTopicName(Long tournamentId) {
        return topicRepository.getTopicName(tournamentId);
    }
}
