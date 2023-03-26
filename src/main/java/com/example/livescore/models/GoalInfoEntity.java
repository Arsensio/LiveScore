package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.assists.GoalInfoDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goal_info")
@ToString
public class GoalInfoEntity extends AbstractEntity<GoalInfoDTO> {

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
    public GoalInfoDTO toDTO() {
        return new GoalInfoDTO(
                player.getName() + " " + player.getSurname(),
                player.getPlayerId()
        );
    }
}
