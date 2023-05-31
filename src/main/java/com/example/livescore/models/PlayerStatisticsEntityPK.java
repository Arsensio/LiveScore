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
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatisticsEntityPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatisticsEntityPK that = (PlayerStatisticsEntityPK) o;
        return Objects.equals(tournament, that.tournament) && Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournament, player);
    }

    @Override
    public String toString() {
        return tournament.getTournamentId()+", "+player.getPlayerId();
    }
}
