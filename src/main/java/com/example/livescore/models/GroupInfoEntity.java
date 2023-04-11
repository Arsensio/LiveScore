package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.group_info.GroupInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_info")
public class GroupInfoEntity extends AbstractEntity<GroupInfoDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_info_id")
    private Long groupIntoId;

    @Column(name = "tournament_logo")
    private String tournamentLogo;
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_logo")
    private String teamLogo;

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

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @OneToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;


    @Override
    public GroupInfoDTO toDTO() {
        return new GroupInfoDTO(
                this.tournament.getTournamentName(),
                this.tournamentLogo,
                this.groupName,
                this.teamName,
                this.teamLogo,
                this.gamePlayed,
                this.winCount,
                this.drawCount,
                this.loseCount,
                this.goalCount,
                this.goalMissed,
                this.points
        );
    }
}
