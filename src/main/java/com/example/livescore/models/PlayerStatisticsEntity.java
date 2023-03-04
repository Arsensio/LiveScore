package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "player_statistics")
public class PlayerStatisticsEntity extends AbstractEntity<PlayerStatisticsDTO> {

    @EmbeddedId
    private PlayerStatisticsEntityPK id; //todo: clarify, should statistic be linked to group or tournament

    @Column(name = "match_played")
    private Long matchPlayed;

    @Column
    private Long goals;

    @Column
    private Long assists;

    @Column(name = "yellow_card")
    private Long yellowCard;

    @Column(name = "red_card")
    private Long redCard;

    @Override
    public PlayerStatisticsDTO toDTO() {
        return new PlayerStatisticsDTO(
                id.getPlayer().getPlayerId(),
                id.getGroup().getGroupId(),
                matchPlayed,
                goals,
                assists,
                yellowCard,
                redCard
        );
    }
}
