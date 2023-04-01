package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.players.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity extends AbstractEntity<PlayerDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamEntity team;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "player_number")
    private Integer playerNumber;

    @Column(name = "role")
    private String role;

    @Override
    public PlayerDTO toDTO() {
        return new PlayerDTO(
                this.playerId,
                this.team.getTeamName(),
                this.name,
                this.surname,
                this.playerNumber,
                this.role
        );
    }
}
