package com.example.livescore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatisticsEntityPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @OneToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

}
