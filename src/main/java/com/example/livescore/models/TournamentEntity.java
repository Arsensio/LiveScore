package com.example.livescore.models;


import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.tournaments.TournamentDTO;
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

    public TournamentDTO toDTO() {
        return new TournamentDTO(
                this.tournamentId,
                this.tournamentName,
                this.tournamentType
        );
    }
}
