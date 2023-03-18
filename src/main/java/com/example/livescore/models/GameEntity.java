package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.games.GameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity extends AbstractEntity<GameDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "is_played")
    private boolean isPlayed;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private GroupEntity group;

    @OneToOne(mappedBy = "game", cascade = CascadeType.MERGE)
    private ProtocolEntity protocol;

    @Override
    public GameDTO toDTO() {
        return new GameDTO(
                gameId,
                group.getGroupId(),
                protocol.getTeam1().getTeamName(),
                protocol.getTeam2().getTeamName(),
                protocol.getTeam1().getTeamLogo(),
                protocol.getTeam2().getTeamLogo(),
                getGameScoreFromProtocol(isPlayed),
                isPlayed,
                protocol.getProtocolId(),
                protocol.getDateAndTime()
        );
    }

    private String getGameScoreFromProtocol(boolean isPlayed) {
        if (isPlayed) {
            return protocol.getTeam1Score() + ":" + protocol.getTeam2Score();
        }

        return "0:0";
    }
}


