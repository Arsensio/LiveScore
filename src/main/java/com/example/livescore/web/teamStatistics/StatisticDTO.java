package com.example.livescore.web.teamStatistics;

import com.example.livescore.enums.EventNames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

import static com.example.livescore.enums.EventNames.RED_CARD;
import static com.example.livescore.enums.EventNames.YELLOW_CARD;

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

    public StatisticDTO(String groupName, String teamName, Long redCard, Integer mathPlayed) {
        this.groupName = groupName;
        this.teamName = teamName;
        this.statCount = redCard.intValue();
        this.mathPlayed = mathPlayed;
    }

    public DistinctTeamStatisticsDTO toDistinctTeamStatisticsDTO(EventNames eventNames) {
        DistinctTeamStatisticsDTO distinctTeamStatisticsDTO = new DistinctTeamStatisticsDTO(eventNames.getEventName(), groupName, teamName);

        setStatAndPerGame(eventNames, distinctTeamStatisticsDTO);

        return distinctTeamStatisticsDTO;
    }

    private void setStatAndPerGame(EventNames eventName, DistinctTeamStatisticsDTO distinctTeamStatisticsDTO) {
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
