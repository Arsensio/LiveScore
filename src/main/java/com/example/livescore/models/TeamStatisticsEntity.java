package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "team_statistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatisticsEntity extends AbstractEntity<TeamStatisticsDTO> {

    @EmbeddedId
    private TeamStatisticsEntityPK id;

    @Column(name = "game_played")
    private Integer gamePlayed;

    @Column(name = "win_count")
    private Integer winCount;

    @Column(name="draw_count")
    private Integer drawCount;

    @Column(name = "lose_count")
    private Integer loseCount;

    @Column(name = "goal_count")
    private Integer goalCount;

    @Column(name = "goal_missed")
    private Integer goalMissed;

    @Column(name = "points")
    private Integer points;

    public TeamStatisticsDTO toDTO() {
        return new TeamStatisticsDTO(
                id.getGroup().getGroupName(),
                id.getTeam().getTeamName(),
                gamePlayed,
                winCount,
                drawCount,
                loseCount,
                goalCount,
                goalMissed,
                points
        );
    }
}
