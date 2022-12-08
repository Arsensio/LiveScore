package com.example.livescore.models;


import com.example.livescore.web.protocols.ProtocolDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "protocols")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protocol_id", nullable = false)
    private Long protocolId;

    @Column(name = "team_1_id")
    private Long team1;

    @Column(name = "team_2_id")
    private Long team2;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @Column(name= "is_finished")
    private boolean isFinished;

    @OneToOne(mappedBy = "protocol")
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameEntity game;

    @Column(name = "game_score")
    private String gameScore;

    public ProtocolDTO toDTO() {
//        return new ProtocolDTO(
//                protocolId,
//                game.getGameId(),
//                team1,
//                team2,
//                dateAndTime,
//                isFinished
//        );
        return null;
    }
}
