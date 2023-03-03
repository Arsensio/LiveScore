package com.example.livescore.models;


import com.example.livescore.web.players.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {

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

    public PlayerDTO toDTO() {
        return new PlayerDTO(
                playerId,
                team.getTeamName(),
                name,
                surname,
                playerNumber,
                role
        );
    }
}
