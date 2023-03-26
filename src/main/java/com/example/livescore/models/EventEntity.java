package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.enums.EventNames;
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
    private String gameScore;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerEntity player;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "penalty")
    private Boolean isPenalty;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private GoalInfoEntity goalInfo;

    public EventEntity(Long eventId, ProtocolEntity protocol, String eventName, String gameScore, PlayerEntity player, Integer minute, Boolean isPenalty) {
        this.eventId = eventId;
        this.protocol = protocol;
        this.eventName = eventName;
        this.gameScore = gameScore;
        this.player = player;
        this.minute = minute;
        this.isPenalty = isPenalty;
    }

    @Override
    public EventDTO toDTO() {
        EventDTO eventDTO = new EventDTO(
                this.eventId,
                this.eventName,
                this.player.getName() + " " + this.player.getSurname(),
                this.minute,
                this.player.getTeam().getTeamId(),
                this.player.getTeam().getTeamName(),
                this.gameScore,
                this.isPenalty
        );
        if (goalInfo != null) {
            if (goalInfo.getName().equals(EventNames.ASSIST.getEventName())) {
                eventDTO.setAssist(goalInfo.toDTO());
                eventDTO.setPenalty(false);
            } else if (goalInfo.getName().equals(EventNames.PENALTY.getEventName())) {
                eventDTO.setAssist(null);
                eventDTO.setPenalty(true);
            }
        }

        return eventDTO;
    }
}
