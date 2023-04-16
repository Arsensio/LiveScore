package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.events.EventDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.livescore.enums.EventEnum.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Builder
public class EventEntity extends AbstractEntity<EventDTO> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "game_score")
    private String gameScore;

    @Column(name = "minute")
    private Integer minute;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "protocol_id", referencedColumnName = "protocol_id")
    private ProtocolEntity protocol;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    List<EventInfoEntity> eventInfo = new ArrayList<>();

    public EventEntity(String gameScore, Integer minute, ProtocolEntity protocol) {
        this.gameScore = gameScore;
        this.minute = minute;
        this.protocol = protocol;
    }

    public EventEntity(Long eventId, String gameScore, Integer minute, ProtocolEntity protocol) {
        this.eventId = eventId;
        this.gameScore = gameScore;
        this.minute = minute;
        this.protocol = protocol;
    }

    @Override
    public EventDTO toDTO() {
        EventDTO eventDTO = EventDTO.builder()
                .eventId(eventId)
                .minute(minute)
                .gameScore(gameScore)
                .build();

        for (EventInfoEntity e : eventInfo) {
            if (e.getEventName().equals(GOAL.getEventName())) {
                setEventInfo(eventDTO, e);
            } else if (e.getEventName().equals(YELLOW_CARD.getEventName())) {
                setEventInfo(eventDTO, e);
            } else if (e.getEventName().equals(RED_CARD.getEventName())) {
                setEventInfo(eventDTO, e);
            } else if (e.getEventName().equals(PENALTY.getEventName())) {
                setEventInfo(eventDTO, e);
            } else if (e.getEventName().equals(MISS_PENALTY.getEventName())) {
                setEventInfo(eventDTO, e);
            } else if (e.getEventName().equals(ASSIST.getEventName())) {
                AssistDTO assist = AssistDTO.builder()
                        .assistPlayer(e.getPlayerName() + " " + e.getPlayerSurname())
                        .assistPlayerId(e.getPlayer().getPlayerId())
                        .build();
                eventDTO.setAssist(assist);
            }
        }

        return eventDTO;
    }

    public EventInfoEntity getEventInfoByEnum(EventEnum eventEnum) {
        for (EventInfoEntity e : eventInfo) {
            if (e.getEventName().equals(eventEnum.getEventName())) {
                return e;
            }
        }
        return null;
    }

    private void setEventInfo(EventDTO eventDTO, EventInfoEntity e) {
        eventDTO.setEventName(e.getEventName());
        eventDTO.setPlayerName(e.getPlayerName() + " " + e.getPlayerSurname());
        eventDTO.setPlayerId(e.getPlayer().getPlayerId());
        eventDTO.setTeamId(e.getTeam().getTeamId());
        eventDTO.setTeamName(e.getTeamName());
    }

    @Override
    public String toString() {

        return "EventEntity{" +
                "eventId=" + eventId +
                ", gameScore='" + gameScore + '\'' +
                ", minute=" + minute +
                ", protocol=" + protocol +
                '}';
    }
}
