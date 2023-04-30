package com.example.livescore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @Column(name = "topic_name")
    private String topicName;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;
}
