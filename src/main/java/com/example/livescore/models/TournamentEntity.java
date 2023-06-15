package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.tournaments.TournamentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tournaments")
public class TournamentEntity extends AbstractEntity<TournamentDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "tournament_name")
    private String tournamentName;

    @Column(name = "tournament_type")
    private String tournamentType;

    @Column(name = "tournament_location")
    private String tournamentLocation;

    @Column(name = "tournament_logo")
    private String tournamentLogo;

    @Column(name = "teams_num")
    private Integer teamsNum;

    @Column(name = "tournament_status")
    private String tournamentStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;


    public TournamentDTO toDTO() {
        return new TournamentDTO(
                this.tournamentId,
                this.tournamentName,
                this.tournamentLogo,
                this.tournamentType,
                this.tournamentLocation,
                this.tournamentStatus
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentEntity that = (TournamentEntity) o;
        return tournamentId.equals(that.tournamentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournamentId);
    }
}
