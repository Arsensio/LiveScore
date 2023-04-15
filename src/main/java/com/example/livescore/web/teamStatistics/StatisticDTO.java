package com.example.livescore.web.teamStatistics;

import com.example.livescore.enums.EventEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

import static com.example.livescore.enums.EventEnum.RED_CARD;
import static com.example.livescore.enums.EventEnum.YELLOW_CARD;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticDTO {

    private String statName;
    private String groupName;
    private String teamName;
    private Integer statCount;
    private Integer mathPlayed;
    private String teamLogo;

    public StatisticDTO(String groupName, String teamName, Long redCard, Integer mathPlayed, String teamLogo) {
        this.groupName = groupName;
        this.teamName = teamName;
        this.statCount = redCard.intValue();
        this.mathPlayed = mathPlayed;
        this.teamLogo = teamLogo;
    }

    public DistinctTeamStatisticsDTO toDistinctTeamStatisticsDTO(EventEnum eventEnum) {
        DistinctTeamStatisticsDTO distinctTeamStatisticsDTO = new DistinctTeamStatisticsDTO(eventEnum.getEventName(),
                groupName, teamName, teamLogo);

        setStatAndPerGame(eventEnum, distinctTeamStatisticsDTO);

        return distinctTeamStatisticsDTO;
    }

    private void setStatAndPerGame(EventEnum eventName, DistinctTeamStatisticsDTO distinctTeamStatisticsDTO) {
        if (eventName.equals(RED_CARD)) {
            DecimalFormat df = new DecimalFormat("0.00");
            double redCard = this.statCount;
            double gamePlayed = this.mathPlayed;
            distinctTeamStatisticsDTO.setTotal(this.statCount);
            distinctTeamStatisticsDTO.setPerGame(df.format(redCard / gamePlayed));
        } else if (eventName.equals(YELLOW_CARD)) {
            DecimalFormat df = new DecimalFormat("0.00");
            double yellowCard = this.statCount;
            double gamePlayed = this.mathPlayed;
            distinctTeamStatisticsDTO.setTotal(this.statCount);
            distinctTeamStatisticsDTO.setPerGame(df.format(yellowCard / gamePlayed));
        }
    }
}
