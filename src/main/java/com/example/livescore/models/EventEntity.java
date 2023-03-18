package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.events.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity extends AbstractEntity<EventDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "protocol_id", referencedColumnName = "protocol_id")
    private ProtocolEntity protocol;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "game_score")
    private String goalScore;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerEntity player;

    @Column(name = "minute")
    private Integer minute;

    @Override
    public EventDTO toDTO() {
        return new EventDTO(
                eventName,
                player.getName()+" "+ player.getSurname(),
                minute,
                player.getTeam().getTeamId(),
                player.getTeam().getTeamName(),
                goalScore
        );
    }
}
