package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

import static com.example.livescore.enums.EventEnum.GOAL;

@Entity
@Table(name = "team_statistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatisticsEntity extends AbstractEntity<TeamStatisticsDTO> {

    @EmbeddedId
    private TeamStatisticsEntityPK id;  // todo: clarify if team statistics should be linked to group

    @Column(name = "game_played")
    private Integer gamePlayed;

    @Column(name = "win_count")
    private Integer winCount;

    @Column(name = "draw_count")
    private Integer drawCount;

    @Column(name = "lose_count")
    private Integer loseCount;

    @Column(name = "goal_count")
    private Integer goalCount;

    @Column(name = "goal_missed")
    private Integer goalMissed;

    @Column(name = "points")
    private Integer points;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @Override
    public TeamStatisticsDTO toDTO() {
        return new TeamStatisticsDTO(
                this.id.getTournament().getTournamentName(),
                this.id.getTeam().getTeamName(),
                this.gamePlayed,
                this.winCount,
                this.drawCount,
                this.loseCount,
                this.goalCount,
                this.goalMissed,
                this.points,
                id.getTeam().getTeamLogo()
        );
    }

    public DistinctTeamStatisticsDTO toDistinctStatisticsDTO(String statName) {
        DistinctTeamStatisticsDTO distinctTeamStatisticsDTO = new DistinctTeamStatisticsDTO(
                statName,
                id.getTournament().getTournamentName(),
                id.getTeam().getTeamName()
        );
        if (statName.equals(GOAL.getEventName())) {
            DecimalFormat df = new DecimalFormat("0.00");
            double goal = goalCount;
            double game = gamePlayed;
            distinctTeamStatisticsDTO.setTotal(goalCount);
            distinctTeamStatisticsDTO.setPerGame(df.format(goal / game));
        }
        distinctTeamStatisticsDTO.setTeamLogo(id.getTeam().getTeamLogo());
        return distinctTeamStatisticsDTO;
    }

    public List<PlayerEntity> getPlayers() {
        return id.getTeam().getPlayers();
    }
}
