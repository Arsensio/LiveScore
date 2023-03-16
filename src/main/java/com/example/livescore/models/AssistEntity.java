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

    @EmbeddedId
    AssistEntityPK id;

    @OneToOne
    @JoinColumn(name = "player_id")
    PlayerEntity player;

    @Override
    public AssistDTO toDTO() {
        return new AssistDTO(
                player.getName() + " " + player.getSurname()
        );
    }
}
