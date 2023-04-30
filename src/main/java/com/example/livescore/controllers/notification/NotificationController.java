package com.example.livescore.controllers.notification;

import com.example.livescore.web.notification.CustomNotificationDto;
import com.example.livescore.web.topic.CreateTopicDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationController {

    ResponseEntity<String> postToTopic(String topic, CustomNotificationDto customNotificationDto);

    ResponseEntity<String> postToClient(CustomNotificationDto customNotificationDto);

    ResponseEntity<List<String>> postToClients(CustomNotificationDto customNotificationDto);

    ResponseEntity<Void> createSubscription(String topic, List<String> registrationTokens);

    ResponseEntity<Void> deleteSubscription(String topic, String registrationToken);

    ResponseEntity<Void> createTopic(CreateTopicDTO createTopicDTO);

    ResponseEntity<String> getTopicName(Long protocolId);
}
