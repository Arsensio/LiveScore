package com.example.livescore.controllers.notification.impl;

import com.example.livescore.controllers.notification.NotificationController;
import com.example.livescore.service.notification.NotificationService;
import com.example.livescore.web.notification.CustomNotificationDto;
import com.example.livescore.web.topic.CreateTopicDTO;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class DefaultNotificationController implements NotificationController {

    private static final String ERROR_MESSAGE = "Error happened while ";
    private static final String POST_NT_SUCCESS_MESSAGE = "Successfully posted message. ID: ";
    private final NotificationService notificationService;

    @PostMapping("/topic/{topic}")
    @Override
    public ResponseEntity<String> postToTopic(@PathVariable String topic, @RequestBody CustomNotificationDto customNotificationDto) {
        log.info("DefaultNotController: postToTopic: {}", customNotificationDto);
        try {
            return ResponseEntity.ok()
                    .body(POST_NT_SUCCESS_MESSAGE + notificationService.postToTopic(topic, customNotificationDto));
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest()
                    .body(ERROR_MESSAGE + "posting to topic\n" + e.getMessage());
        }
    }

    @PostMapping("/client")
    @Override
    public ResponseEntity<String> postToClient(@RequestBody CustomNotificationDto customNotificationDto) {
        log.info("DefaultNotController: postToClient: {}", customNotificationDto);
        try {
            return ResponseEntity.ok()
                    .body(POST_NT_SUCCESS_MESSAGE +
                            notificationService.postToClient(customNotificationDto, customNotificationDto.getRegistrationTokens().get(0))
                    );
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest()
                    .body(ERROR_MESSAGE + "posting to client\n" + e.getMessage());
        }
    }

    @PostMapping("/clients")
    @Override
    public ResponseEntity<List<String>> postToClients(@RequestBody CustomNotificationDto customNotificationDto) {
        log.info("DefaultNotController: postToClients: {}", customNotificationDto);
        try {
            return ResponseEntity.ok()
                    .body(notificationService.postToClients(customNotificationDto, customNotificationDto.getRegistrationTokens()));
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest()
                    .body(List.of(
                            ERROR_MESSAGE + "posting to clients\n" + e.getMessage())
                    );
        }
    }

    @PostMapping("/subscriptions/{topic}")
    @Override
    public ResponseEntity<Void> createSubscription(@PathVariable String topic, @RequestBody List<String> registrationTokens) {
        log.info("DefaultNotController: createSubscription: {}, {}", topic, registrationTokens);
        try {
            notificationService.createSubscription(topic, registrationTokens);
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/subscriptions/{topic}/{registrationToken}")
    @Override
    public ResponseEntity<Void> deleteSubscription(@PathVariable String topic, @PathVariable String registrationToken) {
        log.info("DefaultNotController: createSubscription: {}, {}", topic, registrationToken);
        try {
            notificationService.deleteSubscription(topic, registrationToken);
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/topic/create")
    @Override
    public ResponseEntity<Void> createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        try {
            notificationService.createTopic(createTopicDTO);
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/topic/{protocolId}")
    @Override
    public ResponseEntity<String> getTopicNameByProtocol(@PathVariable Long protocolId) {
        return ResponseEntity.ok().body(
                notificationService.getTopicNameByProtocol(protocolId)
        );
    }

    @GetMapping("/topic/tournament/{tournamentId}")
    public ResponseEntity<String> getTopicNameByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok().body(
                notificationService.getTopicNameByTournament(tournamentId)
        );
    }
}
