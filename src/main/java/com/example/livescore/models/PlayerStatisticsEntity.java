package com.example.livescore.models;

import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "player_statistics")
public class PlayerStatisticsEntity {

    @EmbeddedId
    private PlayerStatisticsEntityPK id;

    @Column(name = "match_played")
    private Integer matchPlayed;

    @Column
    private Integer goals;

    @Column
    private Integer assists;

    @Column(name = "yellow_card")
    private Integer yellowCard;

    @Column(name = "red_card")
    private Integer redCard;

    public PlayerStatisticsDTO toDTO() {
        return new PlayerStatisticsDTO(
                id.getPlayerId(),
                matchPlayed,
                goals,
                assists,
                yellowCard,
                redCard
        );
    }
}
