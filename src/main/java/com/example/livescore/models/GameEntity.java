package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.enums.GameState;
import com.example.livescore.enums.PgSQLEnumType;
import com.example.livescore.web.games.GameDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
@TypeDef(
        name = "game_state",
        typeClass = PgSQLEnumType.class
)
public class GameEntity extends AbstractEntity<GameDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
    @Type(type = "game_state")
    private GameState gameState;

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
                getGameScoreFromProtocol(gameState),
                gameState,
                protocol.getProtocolId(),
                protocol.getDateAndTime()
        );
    }

    private String getGameScoreFromProtocol(GameState gameState) {
        if (gameState == GameState.ENDED || gameState == GameState.STARTED) {
            return protocol.getTeam1Score() + ":" + protocol.getTeam2Score();
        }

        return "0:0";
    }
}


