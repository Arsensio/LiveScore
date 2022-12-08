package com.example.livescore.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class PlayerStatisticsEntityPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    public Long getPlayerId() {
        return player.getPlayerId();
    }
}
