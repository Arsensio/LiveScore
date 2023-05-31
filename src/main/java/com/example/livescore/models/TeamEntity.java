package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.teams.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity extends AbstractEntity<TeamDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_logo")
    private String teamLogo;

    @OneToMany(mappedBy = "team")
    private List<PlayerEntity> players = new ArrayList<>();

    @Override
    public TeamDTO toDTO() {
        return new TeamDTO(
                this.teamId,
                this.teamName,
                this.teamLogo
        );
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
