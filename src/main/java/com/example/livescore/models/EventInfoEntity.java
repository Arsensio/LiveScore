package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.assists.AssistDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_info")
@Builder
@ToString
public class EventInfoEntity extends AbstractEntity<AssistDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_info_id")
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_surname")
    private String playerSurname;

    @Column(name = "player_number")
    private Integer playerNumber;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_logo")
    private String teamLogo;

    @Column(name = "event_name")
    private String eventName;

    @OneToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private EventEntity event;

    public EventInfoEntity(PlayerEntity player, String name, EventEntity event) {
        this.player = player;
        this.eventName = name;
        this.event = event;
    }

    @Override
    public AssistDTO toDTO() {
        return new AssistDTO(
                player.getName() + " " + player.getSurname(),
                player.getPlayerId()
        );
    }
}
