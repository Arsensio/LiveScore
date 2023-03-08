package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.events.EventDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@ToString
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
                player.getTeam().getTeamName()
        );
    }
}
