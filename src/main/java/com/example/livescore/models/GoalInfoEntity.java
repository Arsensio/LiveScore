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
@Table(name = "goal_info")
@ToString
public class GoalInfoEntity extends AbstractEntity<AssistDTO> {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @Column
    private String name;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "event_id")
    private EventEntity event;

    public GoalInfoEntity(PlayerEntity player, String name, EventEntity event) {
        this.player = player;
        this.name = name;
        this.event = event;
    }

    @Override
    public AssistDTO toDTO() {
        return new AssistDTO(
                player.getName() + " " + player.getSurname()
        );
    }
}
