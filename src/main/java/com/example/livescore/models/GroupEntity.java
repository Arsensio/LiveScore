package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.groups.GroupDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
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

    @Column(name = "group_order")
    private Integer groupOrder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return groupId.equals(that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}
