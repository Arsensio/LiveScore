package com.example.livescore.service.notification.impl;

import com.example.livescore.service.notification.NotificationService;
import com.example.livescore.service.topic.TopicService;
import com.example.livescore.web.notification.CustomNotificationDto;
import com.example.livescore.web.topic.CreateTopicDTO;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

    private final FirebaseMessaging fcm;
    private final TopicService topicService;

    @Override
    public String postToTopic(String topic, CustomNotificationDto customNotificationDto) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(customNotificationDto.getTitle())
                .setBody(customNotificationDto.getBody())
                .setImage(customNotificationDto.getImage())
                .build();

        Message msg = Message.builder()
                .setTopic(topic)
                .setNotification(notification)
                .build();

        return fcm.send(msg);
    }

    @Override
    public String postToClient(CustomNotificationDto customNotificationDto, String registrationToken) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(customNotificationDto.getTitle())
                .setBody(customNotificationDto.getBody())
                .setImage(customNotificationDto.getImage())
                .build();

        Message msg = Message.builder()
                .setToken(registrationToken)
                .setNotification(notification)
                .build();

        return fcm.send(msg);
    }

    @Override
    public List<String> postToClients(CustomNotificationDto customNotificationDto, List<String> clientTokens) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(customNotificationDto.getTitle())
                .setBody(customNotificationDto.getBody())
                .setImage(customNotificationDto.getImage())
                .build();

        MulticastMessage msg = MulticastMessage.builder()
                .addAllTokens(clientTokens)
                .setNotification(notification)
                .build();

        BatchResponse response = fcm.sendMulticast(msg);

        return response.getResponses()
                .stream()
                .map(SendResponse::getMessageId).toList();
    }

    @Override
    public void createSubscription(String topic, List<String> registrationTokens) throws FirebaseMessagingException {
        fcm.subscribeToTopic(registrationTokens, topic);
    }

    @Override
    public void deleteSubscription(String topic, String registrationToken) throws FirebaseMessagingException {
        fcm.unsubscribeFromTopic(List.of(registrationToken), topic);
    }

    @Override
    public void createTopic(CreateTopicDTO createTopicDTO) throws FirebaseMessagingException {
        createSubscription(createTopicDTO.getTopicName(), List.of(createTopicDTO.getRegistrationToken()));
        topicService.createTopic(createTopicDTO.getTopicName(), createTopicDTO.getTournamentId());
    }

    @Override
    public String getTopicNameByProtocol(Long protocolId) {
        return topicService.getTopicNameByProtocol(protocolId);
    }

    @Override
    public String getTopicNameByTournament(Long tournamentId) {
        return topicService.getTopicNameByTournament(tournamentId);
    }
}
