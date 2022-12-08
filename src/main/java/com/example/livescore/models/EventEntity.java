package com.example.livescore.models;


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
public class EventEntity {
    @EmbeddedId
    private EventPk protocolId;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerEntity player;

    @Column(name ="minute")
    private Integer minute;

    @Column(name = "event_name")
    private String eventName;

    public EventDTO toDTO() {
        return new EventDTO(
                protocolId.getProtocolId(),
                player.getName(),
                minute,
                eventName
        );
    }
}
