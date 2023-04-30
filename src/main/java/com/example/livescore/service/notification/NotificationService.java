package com.example.livescore.service.notification;

import com.example.livescore.web.notification.CustomNotificationDto;
import com.example.livescore.web.topic.CreateTopicDTO;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface NotificationService {

    String postToTopic(String topic, CustomNotificationDto customNotificationDto) throws FirebaseMessagingException;

    String postToClient(CustomNotificationDto customNotificationDto, String registrationToken) throws FirebaseMessagingException;

    List<String> postToClients(CustomNotificationDto customNotificationDto, List<String> clientTokens) throws FirebaseMessagingException;

    void createSubscription(String topic, List<String> registrationTokens) throws FirebaseMessagingException;

    void deleteSubscription(String topic, String registrationToken) throws FirebaseMessagingException;

    void createTopic(CreateTopicDTO createTopicDTO) throws FirebaseMessagingException;

    String getTopicName(Long protocolId);
}
