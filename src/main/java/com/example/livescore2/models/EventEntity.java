package com.example.livescore2.models;


import com.example.livescore.web.events.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class EventEntity {
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

    @Column(name ="minute")
    private Integer minute;

    public EventDTO toDTO() {
        return new EventDTO(
                eventId,
                protocol.getProtocolId(),
                eventName,
                player.getName(),
                minute
        );
    }
}
