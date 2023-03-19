package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.events.EventDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@ToString
public class EventEntity extends AbstractEntity<EventDTO> implements Serializable {
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

    @Column(name = "penalty")
    private Boolean isPenalty;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private AssistEntity assist;

    public EventEntity(Long eventId, ProtocolEntity protocol, String eventName, String goalScore, PlayerEntity player, Integer minute, Boolean isPenalty) {
        this.eventId = eventId;
        this.protocol = protocol;
        this.eventName = eventName;
        this.goalScore = goalScore;
        this.player = player;
        this.minute = minute;
        this.isPenalty = isPenalty;
    }

    @Override
    public EventDTO toDTO() {
        EventDTO eventDTO = new EventDTO(
                eventName,
                player.getName() + " " + player.getSurname(),
                minute,
                player.getTeam().getTeamId(),
                player.getTeam().getTeamName(),
                goalScore,
                isPenalty
        );
        if (assist == null) {
            eventDTO.setAssist(null);
        } else {
            eventDTO.setAssist(assist.toDTO());
        }

        return eventDTO;
    }
}
