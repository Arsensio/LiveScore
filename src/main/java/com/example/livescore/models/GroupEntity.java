package com.example.livescore.models;


import com.example.livescore.web.groups.GroupDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class GroupEntity {

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

    public GroupDTO toDTO() {
        return new GroupDTO(
                this.groupId,
                this.tournament.getTournamentName(),
                this.groupName,
                this.isPlayoff
        );
    }
}
