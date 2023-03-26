package com.example.livescore.service.event.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.*;
import com.example.livescore.repository.EventRepository;
import com.example.livescore.service.event.EventService;
import com.example.livescore.service.goal_info.GoalInfoService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.livescore.enums.EventNames.ASSIST;
import static com.example.livescore.enums.EventNames.GOAL;

@Service
public class DefaultEventService
        extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long, EventRepository>
        implements EventService {

    private final PlayerService playerService;
    private final ProtocolService protocolService;
    private final PlayerStatisticsService playerStatisticsService;
    private final TeamStatisticsService teamStatisticsService;
    private final GoalInfoService goalInfoService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultEventService(EventRepository repository, PlayerService playerService, ProtocolService protocolService, PlayerStatisticsService playerStatisticsService, TeamStatisticsService teamStatisticsService, GoalInfoService goalInfoService) {
        super(repository);
        this.playerService = playerService;
        this.protocolService = protocolService;
        this.playerStatisticsService = playerStatisticsService;
        this.teamStatisticsService = teamStatisticsService;
        this.goalInfoService = goalInfoService;
    }


    @Override
    @Transactional
    public EventDTO saveGoal(SaveGoalEventDTO dto) {
        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        GroupEntity group = protocol.getGame().getGroup();
        PlayerEntity goalPlayer = playerService.findEntityById(dto.getPlayerId());

        updatePlayerStatistic(group, goalPlayer, GOAL, protocol);
        EventEntity save = repository.save(getNewGoalEvent(dto, protocol, goalPlayer));

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            updatePlayerStatistic(group, assistPlayer, ASSIST, protocol);
            goalInfoService.saveAssist(assistPlayer, save);
        } else if (dto.getIsPenalty()) {
            goalInfoService.savePenalty(null, save);
        }

        log.info("CREATE NEW GOAL EVENT {}", save);

        return save.toDTO();
    }

    @Override
    @Transactional
    public EventDTO save(SaveEventDTO dto) {
        EventNames event = EventNames.getEventById(dto.getEventEnumId());

        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        PlayerEntity player = playerService.findEntityById(dto.getPlayerId());
        GroupEntity group = protocol.getGame().getGroup();

        updatePlayerStatistic(group, player, event, protocol);
        EventEntity save = repository.save(getNewGoalEvent(dto, event, protocol, player));
        log.info("CREATE NEW EVENT {}", save);

        return save.toDTO();
    }

    private EventEntity getNewGoalEvent(SaveEventDTO dto, EventNames event, ProtocolEntity protocol, PlayerEntity player) {
        return new EventEntity(
                null,
                protocol,
                event.getEventName(),
                protocol.getTeam1Score() + ":" + protocol.getTeam2Score(),
                player,
                dto.getMinute(),
                false
        );
    }

    private EventEntity getNewGoalEvent(SaveGoalEventDTO dto, ProtocolEntity protocol, PlayerEntity player) {
        return new EventEntity(
                null,
                protocol,
                GOAL.getEventName(),
                protocol.getTeam1Score() + ":" + protocol.getTeam2Score(),
                player,
                dto.getMinute(),
                dto.getIsPenalty()
        );
    }


    private void updatePlayerStatistic(GroupEntity group, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(group, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        log.info(foundPlayerStat.toString());
        if (eventName == GOAL) {
            increaseAndDecreaseGoalCount(group, player, protocol, foundPlayerStat);
        } else if (eventName == ASSIST) {
            foundPlayerStat.setAssists(foundPlayerStat.getAssists() + 1);
        } else if (eventName == EventNames.RED_CARD) {
            foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() + 1);
        } else if (eventName == EventNames.YELLOW_CARD) {
            foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() + 1);
        } else if (eventName == EventNames.SCORE_PENALTY) {
            increaseAndDecreaseGoalCount(group, player, protocol, foundPlayerStat);
        }

        log.info("UPDATE PLAYER STATISTICS");
        log.info(foundPlayerStat.toString());
        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void increaseAndDecreaseGoalCount(GroupEntity group, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
        foundPlayerStat.setGoals(foundPlayerStat.getGoals() + 1);
        TeamEntity team1 = protocol.getTeam1();
        TeamEntity team2 = protocol.getTeam2();

        if (player.getTeam().equals(team1)) {
            updateTeamStat(group, team1, team2);
            protocol.setTeam1Score(protocol.getTeam1Score() + 1);
        } else {
            updateTeamStat(group, team2, team1);
            protocol.setTeam2Score(protocol.getTeam2Score() + 1);
        }

        protocolService.saveAndFlush(protocol);
        log.info("UPDATE PROTOCOL SCORE " + protocol.getTeam1() + " " + protocol.getTeam1Score() + " : " + protocol.getTeam2Score() + " " + protocol.getTeam2());
    }

    private void updateTeamStat(GroupEntity group, TeamEntity goalScoredTeam, TeamEntity goalMissedTeam) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(group, goalScoredTeam);
        teamStatisticsService.incrementGoalCount(teamStatisticsEntityPK);
        TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(group, goalMissedTeam);
        teamStatisticsService.incrementGoalMissedCount(team2StatisticsEntityPK);
    }

}
