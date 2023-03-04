package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "protocols")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolEntity extends AbstractEntity<ProtocolDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protocol_id", nullable = false)
    private Long protocolId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
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

    @Override
    public ProtocolDTO toDTO() {
        return new ProtocolDTO(
                protocolId,
                game.getGameId(),
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
