package com.example.livescore.service.event.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.*;
import com.example.livescore.repository.EventRepository;
import com.example.livescore.service.event.EventService;
import com.example.livescore.service.goal_info.GoalInfoService;
import com.example.livescore.service.group_info.GroupInfoService;
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
public class DefaultEventService
        extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long, EventRepository>
        implements EventService {

    private final PlayerService playerService;
    private final ProtocolService protocolService;
    private final PlayerStatisticsService playerStatisticsService;
    private final TeamStatisticsService teamStatisticsService;
    private final GoalInfoService goalInfoService;
    private final GroupInfoService groupInfoService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultEventService(EventRepository repository, PlayerService playerService, ProtocolService protocolService, PlayerStatisticsService playerStatisticsService, TeamStatisticsService teamStatisticsService, GoalInfoService goalInfoService, GroupInfoService groupInfoService) {
        super(repository);
        this.playerService = playerService;
        this.protocolService = protocolService;
        this.playerStatisticsService = playerStatisticsService;
        this.teamStatisticsService = teamStatisticsService;
        this.goalInfoService = goalInfoService;
        this.groupInfoService = groupInfoService;
    }


    @Override
    @Transactional
    public EventDTO saveGoal(SaveGoalEventDTO dto) {
        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();
        PlayerEntity goalPlayer = playerService.findEntityById(dto.getPlayerId());

        increasePlayerStatistic(tournament, goalPlayer, GOAL, protocol);
        EventEntity save = repository.save(getNewGoalEvent(dto, protocol, goalPlayer));

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(tournament, assistPlayer, ASSIST, protocol);
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
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        rollBackPlayerStatistics(tournament, event.getPlayer(), EventNames.valueOf(event.getEventName()), protocol);

        if (goalInfo != null) {
            if (goalInfo.getName().equals(ASSIST.getEventName())) {
                PlayerEntity assistPlayer = goalInfo.getPlayer();
                rollBackPlayerStatistics(tournament, assistPlayer, ASSIST, protocol);
            }

            goalInfoService.delete(goalInfo.getId());
        }

        event.setPlayer(playerService.findEntityById(dto.getPlayerId()));
        event.setMinute(dto.getMinute());

        increasePlayerStatistic(tournament, event.getPlayer(), GOAL, protocol);
        returnDto = repository.saveAndFlush(event).toDTO();

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(tournament, assistPlayer, ASSIST, protocol);
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
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        increasePlayerStatistic(tournament, player, event, protocol);
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
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        rollBackPlayerStatistics(tournament, event.getPlayer(), EventNames.valueOf(event.getEventName()), protocol);

        EventNames newEventName = getEventById(dto.getEventEnumId());
        PlayerEntity newPlayer = playerService.findEntityById(dto.getPlayerId());
        increasePlayerStatistic(tournament, newPlayer, newEventName, protocol);

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


    private void increasePlayerStatistic(TournamentEntity tournament, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(tournament, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        log.info(foundPlayerStat.toString());
        if (eventName == GOAL) {
            postGoalCount(tournament, player, protocol, foundPlayerStat);
        } else if (eventName == ASSIST) {
            foundPlayerStat.setAssists(foundPlayerStat.getAssists() + 1);
        } else if (eventName == RED_CARD) {
            foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() + 1);
        } else if (eventName == YELLOW_CARD) {
            foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() + 1);
        } else if (eventName == SCORE_PENALTY) {
            postGoalCount(tournament, player, protocol, foundPlayerStat);
        }

        log.info("UPDATE PLAYER STATISTICS");
        log.info(foundPlayerStat.toString());
        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void rollBackPlayerStatistics(TournamentEntity tournament, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(tournament, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        if (eventName == GOAL) {
            rollbackGoalCount(tournament, player, protocol, foundPlayerStat);
        } else if (eventName == ASSIST) {
            foundPlayerStat.setAssists(foundPlayerStat.getAssists() - 1);
        } else if (eventName == RED_CARD) {
            foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() - 1);
        } else if (eventName == YELLOW_CARD) {
            foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() - 1);
        } else if (eventName == SCORE_PENALTY) {
            rollbackGoalCount(tournament, player, protocol, foundPlayerStat);
        }

        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void postGoalCount(TournamentEntity tournament, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
        foundPlayerStat.setGoals(foundPlayerStat.getGoals() + 1);
        TeamEntity team1 = protocol.getTeam1();
        TeamEntity team2 = protocol.getTeam2();

        if (player.getTeam().equals(team1)) {
            updateTeamStat(protocol.getGame().getGroup(), tournament, team1, team2);
            protocol.setTeam1Score(protocol.getTeam1Score() + 1);
        } else {
            updateTeamStat(protocol.getGame().getGroup(), tournament, team2, team1);
            protocol.setTeam2Score(protocol.getTeam2Score() + 1);
        }

        protocolService.saveAndFlush(protocol);
        log.info("UPDATE PROTOCOL SCORE " + protocol.getTeam1() + " " + protocol.getTeam1Score() + " : " + protocol.getTeam2Score() + " " + protocol.getTeam2());
    }

    private void rollbackGoalCount(TournamentEntity tournament, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
        foundPlayerStat.setGoals(foundPlayerStat.getGoals() - 1);
        TeamEntity team1 = protocol.getTeam1();
        TeamEntity team2 = protocol.getTeam2();

        if (player.getTeam().equals(team1)) {
            rollbackTeamStat(protocol.getGame().getGroup(), tournament, team1, team2);
            protocol.setTeam1Score(protocol.getTeam1Score() - 1);
        } else {
            rollbackTeamStat(protocol.getGame().getGroup(), tournament, team2, team1);
            protocol.setTeam2Score(protocol.getTeam2Score() - 1);
        }
    }

    private void updateTeamStat(GroupEntity group, TournamentEntity tournament, TeamEntity goalScoredTeam, TeamEntity goalMissedTeam) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(tournament, goalScoredTeam);
        teamStatisticsService.incrementGoalCount(teamStatisticsEntityPK);
        TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(tournament, goalMissedTeam);
        teamStatisticsService.incrementGoalMissedCount(team2StatisticsEntityPK);

        groupInfoService.incrementGoalCount(group, goalScoredTeam);
        groupInfoService.incrementGoalMissedCount(group, goalMissedTeam);
    }

    private void rollbackTeamStat(GroupEntity group, TournamentEntity tournament, TeamEntity goalScoredTeam, TeamEntity goalMissedTeam) {
        TeamStatisticsEntityPK teamStatisticsEntityPK = new TeamStatisticsEntityPK(tournament, goalScoredTeam);
        teamStatisticsService.decrementGoalCount(teamStatisticsEntityPK);
        TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(tournament, goalMissedTeam);
        teamStatisticsService.decrementGoalMissedCount(team2StatisticsEntityPK);

        groupInfoService.decrementGoalCount(group, goalScoredTeam);
        groupInfoService.decrementGoalMissedCount(group, goalMissedTeam);
    }

}
