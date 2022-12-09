package com.example.livescore.models;


import com.example.livescore.web.games.GameDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "is_played")
    private boolean isPlayed;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private GroupEntity group;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protocol_id", referencedColumnName = "protocol_id")
    private ProtocolEntity protocol;

    public GameDTO toDTO() {
        return new GameDTO(
                gameId,
                isPlayed,
                getGameScoreFromProtocol()
        );
    }

    private String getGameScoreFromProtocol() {
        return protocol.getTeam1Score() + ":" + protocol.getTeam2Score();
    }
}
