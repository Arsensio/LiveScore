package com.example.livescore.controllers.notification.impl;

import com.example.livescore.service.topic.TopicService;
import com.example.livescore.web.message.ConditionMessageRepresentation;
import com.example.livescore.web.message.MulticastMessageRepresentation;
import com.example.livescore.web.topic.CreateTopicDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class DefaultNotificationController {

    private final FirebaseMessaging fcm;
    private final TopicService topicService;
    private final GoogleCredentials googleCredentials;
    private final ResourceLoader resourceLoader;

    @PostMapping("/topics/{topic}/{message}")
    public ResponseEntity<String> postToTopic(@PathVariable String message, @PathVariable("topic") String topic) throws FirebaseMessagingException {
        System.out.println("Controller, Topic: " + topic);
        System.out.println("Controller, Message: " + message);
        Message msg = Message.builder()
                .setTopic(topic)
                .putData("body", message)
                .build();

        String id = fcm.send(msg);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(id);
    }

    @PostMapping("/condition")
    public ResponseEntity<String> postToCondition(@RequestBody ConditionMessageRepresentation message) throws FirebaseMessagingException {

        Message msg = Message.builder()
                .setCondition(message.getCondition())
                .putData("body", message.getData())
                .build();

        String id = fcm.send(msg);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(id);
    }

    @PostMapping("/clients/{registrationToken}")
    public ResponseEntity<String> postToClient(@RequestBody String message, @PathVariable("registrationToken") String registrationToken) throws FirebaseMessagingException {

        Message msg = Message.builder()
                .setToken(registrationToken)
                .putData("body", message)
                .build();

        String id = fcm.send(msg);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<List<String>> postToClients(@RequestBody MulticastMessageRepresentation message) throws FirebaseMessagingException {

        MulticastMessage msg = MulticastMessage.builder()
                .addAllTokens(message.getRegistrationTokens())
                .putData("body", message.getData())
                .build();

        BatchResponse response = fcm.sendMulticast(msg);

        List<String> ids = response.getResponses()
                .stream()
                .map(r -> r.getMessageId())
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ids);
    }

    @PostMapping("/subscriptions/{topic}")
    public ResponseEntity<Void> createSubscription(@PathVariable("topic") String topic, @RequestBody List<String> registrationTokens) throws FirebaseMessagingException {
        fcm.subscribeToTopic(registrationTokens, topic);
        registrationTokens.stream().forEach(System.out::println);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscriptions/{topic}/{registrationToken}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String topic, @PathVariable String registrationToken) throws FirebaseMessagingException {
        fcm.subscribeToTopic(Arrays.asList(registrationToken), topic);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        fcm.subscribeToTopicAsync(List.of(createTopicDTO.getRegistrationToken()), createTopicDTO.getTopicName());
        topicService.createTopic(createTopicDTO.getTopicName(), createTopicDTO.getTournamentId());
        System.out.println("=====================   Topic created. Name: " + topicService.getTopicName(createTopicDTO.getTournamentId()) + " ==============");
        return ResponseEntity.ok().body("Topic create successfully!");
    }

    @GetMapping("/asd")
    public String asd() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:livescoresdu-firebase-adminsdk-6p6r9-e69205f00f.json");
        // Load the service account key JSON file
        InputStream serviceAccount = resource.getInputStream();

// Authenticate a Google credential with the service account
        GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);

// Add the required scopes to the Google credential
        GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email"
                )
        );

// Use the Google credential to generate an access token
        scoped.refreshToken();
        String token = scoped.getAccessToken();

// See the "Using the access token" section below for information
// on how to use the access token to send authenticated requests to the
// Realtime Database REST API.
        return token;
    }
}
