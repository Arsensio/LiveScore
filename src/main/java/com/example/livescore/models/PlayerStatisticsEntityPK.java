package com.example.livescore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatisticsEntityPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;
}
