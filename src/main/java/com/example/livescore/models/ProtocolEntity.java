package com.example.livescore.models;


import com.example.livescore.web.protocols.ProtocolDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "protocols")
@Getter
@Setter
@NoArgsConstructor
public class ProtocolEntity {

    public ProtocolEntity(Long protocolId, GameEntity game, TeamEntity team1, TeamEntity team2, LocalDateTime dateAndTime, int team1Score, int team2Score) {
        this.protocolId = protocolId;
        this.game = game;
        this.team1 = team1;
        this.team2 = team2;
        this.dateAndTime = dateAndTime;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protocol_id", nullable = false)
    private Long protocolId;

    @OneToOne(mappedBy = "protocol")
    private GameEntity game;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_1_id", referencedColumnName = "team_id")
    private TeamEntity team1;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_2_id", referencedColumnName = "team_id")
    private TeamEntity team2;

    @Column(name = "date_time")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime dateAndTime;

    @Column(name = "team_1_score")
    private int team1Score;

    @Column(name = "team_2_score")
    private int team2Score;

    @JsonIgnore
    @OneToMany(mappedBy = "protocol")
    List<EventEntity> events = new ArrayList<>();

    public ProtocolDTO toDTO() {
        return new ProtocolDTO(
                protocolId,
                team1.getTeamName(),
                team2.getTeamName(),
                dateAndTime,
                gameScore()
        );
    }

    private String gameScore() {
        return team1Score + ":" + team2Score;
    }

    public void incrementTeamScore(boolean first) {
        if (first)
            team1Score++;
        else
            team2Score++;
    }
}
