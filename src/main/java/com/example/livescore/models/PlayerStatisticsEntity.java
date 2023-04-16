package com.example.livescore.models;

import com.example.core.dto.AbstractEntity;
import com.example.livescore.web.playerStatistics.PlayerStatisticsDTO;
import com.example.livescore.web.players.DistinctPlayerStatisticsDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DecimalFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "player_statistics")
@ToString
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
                id.getTournament().getTournamentId(),
                matchPlayed,
                goals,
                assists,
                yellowCard,
                redCard
        );
    }

    public DistinctPlayerStatisticsDTO distinctDTO(String statName) {
        DecimalFormat df = new DecimalFormat("0.00");

        DistinctPlayerStatisticsDTO distinctPlayerStatisticsDTO = new DistinctPlayerStatisticsDTO(
                statName,
//                this.id.getGroup().getGroupName(),
                this.id.getPlayer().getTeam().getTeamName(),
                this.id.getPlayer().getTeam().getTeamLogo(),
                this.id.getPlayer().getName() + " " + this.id.getPlayer().getSurname()
        );

        setTotalAndPerGame(statName, df, distinctPlayerStatisticsDTO);

        return distinctPlayerStatisticsDTO;
    }

    private void setTotalAndPerGame(String statName, DecimalFormat df, DistinctPlayerStatisticsDTO distinctPlayerStatisticsDTO) {
        switch (statName) {
            case "ASSISTS" -> {
                double assist = this.assists;
                double game = matchPlayed;
                distinctPlayerStatisticsDTO.setTotal(this.assists);
                distinctPlayerStatisticsDTO.setPerGame(df.format(assist / game));
                break;
            }
            case "GOALS" -> {
                double goal = this.goals;
                double game = matchPlayed;
                distinctPlayerStatisticsDTO.setTotal(this.goals);
                distinctPlayerStatisticsDTO.setPerGame(df.format(goal / game));
                break;
            }
            case "YELLOW CARD" -> {
                double yellowCard = this.yellowCard;
                double game = matchPlayed;
                distinctPlayerStatisticsDTO.setTotal(this.yellowCard);
                distinctPlayerStatisticsDTO.setPerGame(df.format(yellowCard / game));
                break;
            }
            case "RED CARD" -> {
                double redCard = this.redCard;
                double game = matchPlayed;
                distinctPlayerStatisticsDTO.setTotal(this.redCard);
                distinctPlayerStatisticsDTO.setPerGame(df.format(redCard / game));
                break;
            }
        }
    }
}
