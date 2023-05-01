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
public class TeamStatisticsEntityPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @OneToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamStatisticsEntityPK that = (TeamStatisticsEntityPK) o;
        return Objects.equals(tournament, that.tournament) && Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournament, team);
    }
}
