package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.teams.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "teams")
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
}
