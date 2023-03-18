package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.groups.GroupDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity extends AbstractEntity<GroupDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "tournament_id")
    TournamentEntity tournament;

    @Column(name = "group_name")
    private String groupName;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<GameEntity> games;

    @Column(name = "is_playoff")
    private boolean isPlayoff;

    @Override
    public GroupDTO toDTO() {
        return new GroupDTO(
                this.groupId,
                this.tournament.getTournamentName(),
                this.groupName,
                this.isPlayoff
        );
    }
}
