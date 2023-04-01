package com.example.livescore.service.event.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
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

import java.util.Optional;

import static com.example.livescore.enums.EventNames.*;

@Service
public class DefaultEventService extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long,
        EventRepository>
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

        increasePlayerStatistic(group, goalPlayer, GOAL, protocol);
        EventEntity save = repository.save(getNewGoalEvent(dto, protocol, goalPlayer));

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(group, assistPlayer, ASSIST, protocol);
            goalInfoService.saveAssist(assistPlayer, save);
        } else if (dto.getIsPenalty()) {
            goalInfoService.savePenalty(null, save);
        }

        log.info("CREATE NEW GOAL EVENT {}", save);

        return save.toDTO();
    }

    @Override
    @Transactional
    public EventDTO updateGoal(Long id, SaveGoalEventDTO dto) {
        Optional<EventEntity> foundEvent = repository.findById(id);

        if (foundEvent.isEmpty()) {
            throw ResourceNotFoundException.build(id, "EventEntity");
        }
        EventDTO returnDto;
        EventEntity event = foundEvent.get();
        GoalInfoEntity goalInfo = goalInfoService.findEntityById(id);
        ProtocolEntity protocol = event.getProtocol();
        GroupEntity group = protocol.getGame().getGroup();

        rollBackPlayerStatistics(group, event.getPlayer(), EventNames.valueOf(event.getEventName()), protocol);

        if (goalInfo != null) {
            if (goalInfo.getName().equals(ASSIST.getEventName())) {
                PlayerEntity assistPlayer = goalInfo.getPlayer();
                rollBackPlayerStatistics(group, assistPlayer, ASSIST, protocol);
            }

            goalInfoService.delete(goalInfo.getId());
        }

        event.setPlayer(playerService.findEntityById(dto.getPlayerId()));
        event.setMinute(dto.getMinute());

        increasePlayerStatistic(group, event.getPlayer(), GOAL, protocol);
        returnDto = repository.saveAndFlush(event).toDTO();

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(group, assistPlayer, ASSIST, protocol);
            GoalInfoEntity saveAssist = goalInfoService.saveAssist(assistPlayer, event);
            returnDto.setAssist(saveAssist.toDTO());
        } else if (dto.getIsPenalty()) {
            returnDto.setPenalty(true);
            goalInfoService.savePenalty(null, event);
        }

        return returnDto;
    }

    @Override
    @Transactional
    public EventDTO save(SaveEventDTO dto) {
        EventNames event = EventNames.getEventById(dto.getEventEnumId());

        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        PlayerEntity player = playerService.findEntityById(dto.getPlayerId());
        GroupEntity group = protocol.getGame().getGroup();

        increasePlayerStatistic(group, player, event, protocol);
        EventEntity save = repository.save(getNewGoalEvent(dto, event, protocol, player));
        log.info("CREATE NEW EVENT {}", save);

        return save.toDTO();
    }

    @Override
    @Transactional
    public EventDTO update(Long id, SaveEventDTO dto) {
        Optional<EventEntity> foundEvent = repository.findById(id);

        if (foundEvent.isEmpty()) {
            throw ResourceNotFoundException.build(id, "EventEntity");
        }

        EventEntity event = foundEvent.get();
        ProtocolEntity protocol = event.getProtocol();
        GroupEntity group = protocol.getGame().getGroup();

        rollBackPlayerStatistics(group, event.getPlayer(), EventNames.valueOf(event.getEventName()), protocol);

        EventNames newEventName = getEventById(dto.getEventEnumId());
        PlayerEntity newPlayer = playerService.findEntityById(dto.getPlayerId());
        increasePlayerStatistic(group, newPlayer, newEventName, protocol);

        event.setPlayer(newPlayer);
        event.setEventName(getEventNameById(dto.getEventEnumId()));
        event.setMinute(dto.getMinute());
        repository.saveAndFlush(event);

        return event.toDTO();
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


    private void increasePlayerStatistic(GroupEntity group, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(group, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        log.info(foundPlayerStat.toString());
        if (eventName == GOAL) {
            postGoalCount(group, player, protocol, foundPlayerStat);
        } else if (eventName == ASSIST) {
            foundPlayerStat.setAssists(foundPlayerStat.getAssists() + 1);
        } else if (eventName == RED_CARD) {
            foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() + 1);
        } else if (eventName == YELLOW_CARD) {
            foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() + 1);
        } else if (eventName == SCORE_PENALTY) {
            postGoalCount(group, player, protocol, foundPlayerStat);
        }

        log.info("UPDATE PLAYER STATISTICS");
        log.info(foundPlayerStat.toString());
        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void rollBackPlayerStatistics(GroupEntity group, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(group, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        if (eventName == GOAL) {
            rollbackGoalCount(group, player, protocol, foundPlayerStat);
        } else if (eventName == ASSIST) {
            foundPlayerStat.setAssists(foundPlayerStat.getAssists() - 1);
        } else if (eventName == RED_CARD) {
            foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() - 1);
        } else if (eventName == YELLOW_CARD) {
            foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() - 1);
        } else if (eventName == SCORE_PENALTY) {
            rollbackGoalCount(group, player, protocol, foundPlayerStat);
        }

        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void postGoalCount(GroupEntity group, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
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

    private void rollbackGoalCount(GroupEntity group, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
        foundPlayerStat.setGoals(foundPlayerStat.getGoals() - 1);
        TeamEntity team1 = protocol.getTeam1();
        TeamEntity team2 = protocol.getTeam2();

        if (player.getTeam().equals(team1)) {
            rollbackTeamStat(group, team1, team2);
            protocol.setTeam1Score(protocol.getTeam1Score() - 1);
        } else {
            rollbackTeamStat(group, team2, team1);
            protocol.setTeam2Score(protocol.getTeam2Score() - 1);
        }
    }

    private void updateTeamStat(GroupEntity group, TeamEntity goalScoredTeam, TeamEntity goalMissedTeam) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(group, goalScoredTeam);
        teamStatisticsService.incrementGoalCount(teamStatisticsEntityPK);
        TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(group, goalMissedTeam);
        teamStatisticsService.incrementGoalMissedCount(team2StatisticsEntityPK);
    }

    private void rollbackTeamStat(GroupEntity group, TeamEntity goalScoredTeam, TeamEntity goalMissedTeam) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(group, goalScoredTeam);
        teamStatisticsService.decrementGoalCount(teamStatisticsEntityPK);
        TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(group, goalMissedTeam);
        teamStatisticsService.decrementGoalMissedCount(team2StatisticsEntityPK);
    }

}
