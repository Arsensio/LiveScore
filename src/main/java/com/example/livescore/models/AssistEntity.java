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
@Table(name = "assists")
@ToString
public class AssistEntity extends AbstractEntity<AssistDTO> {

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

    public AssistEntity(Long id, PlayerEntity player, String name) {
        this.id = id;
        this.player = player;
        this.name = name;
    }

    @Override
    public AssistDTO toDTO() {

        return new AssistDTO(
                player.getName() + " " + player.getSurname()
        );
    }
}
