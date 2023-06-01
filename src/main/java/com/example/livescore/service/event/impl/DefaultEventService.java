package com.example.livescore.service.event.impl;

import com.example.core.exception.exceptions.EventException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.models.*;
import com.example.livescore.repository.EventRepository;
import com.example.livescore.service.even_info.EventInfoService;
import com.example.livescore.service.event.EventService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.player_statistics.PlayerStatisticsService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.events.AbstractSaveEventDTO;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.core.enums.ErrorMessage.*;
import static com.example.livescore.enums.EventEnum.*;

@Service
public class DefaultEventService
        extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long, EventRepository>
        implements EventService {

    private final PlayerService playerService;
    private final ProtocolService protocolService;
    private final PlayerStatisticsService playerStatisticsService;
    private final TeamStatisticsService teamStatisticsService;
    private final EventInfoService eventInfoService;
    private final GroupInfoService groupInfoService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultEventService(EventRepository repository, PlayerService playerService, ProtocolService protocolService, PlayerStatisticsService playerStatisticsService, TeamStatisticsService teamStatisticsService, EventInfoService eventInfoService, GroupInfoService groupInfoService) {
        super(repository);
        this.playerService = playerService;
        this.protocolService = protocolService;
        this.playerStatisticsService = playerStatisticsService;
        this.teamStatisticsService = teamStatisticsService;
        this.eventInfoService = eventInfoService;
        this.groupInfoService = groupInfoService;
    }

    @Override
    @Transactional
    public EventDTO saveGoal(SaveGoalEventDTO dto) {
        List<EventInfoEntity> eventInfos = new ArrayList<>();

        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();
        PlayerEntity goalPlayer = playerService.findEntityById(dto.getPlayerId());

        checkForRedCard(protocol, goalPlayer);

        increasePlayerStatistic(tournament, goalPlayer, GOAL, protocol);
        EventEntity save = repository.save(newEvent(dto, protocol));

        EventInfoEntity goalEventInfo = eventInfoService.saveEventInfo(newEventInfo(save, goalPlayer, GOAL));
        eventInfos.add(goalEventInfo);

        if (dto.getAssistId() != null && dto.getAssistId() != 0) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(tournament, assistPlayer, ASSIST, protocol);

            EventInfoEntity assistInfoEntity = eventInfoService.saveEventInfo(newEventInfo(save, assistPlayer, ASSIST));
            eventInfos.add(assistInfoEntity);
        }

        save.setEventInfo(eventInfos);
        return save.toDTO();
    }

    @Override
    @Transactional
    public EventDTO updateGoal(Long id, SaveGoalEventDTO dto) {
        EventEntity event = this.findEntityById(id);
        EventInfoEntity goalInfo = event.getEventInfoByEnum(GOAL);

        ProtocolEntity protocol = event.getProtocol();
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        //rollback statistics and delete eventInfo
        rollBackPlayerStatistics(tournament, goalInfo.getPlayer(), EventEnum.valueOf(goalInfo.getEventName()), protocol);
        eventInfoService.delete(goalInfo.getId());

        EventInfoEntity assistInfo = event.getEventInfoByEnum(ASSIST);
        if (assistInfo != null) {
            PlayerEntity assistPlayer = assistInfo.getPlayer();
            rollBackPlayerStatistics(tournament, assistPlayer, ASSIST, protocol);
            eventInfoService.delete(assistInfo.getId());
        }

        // start update info about goal and assist
        PlayerEntity newGoalAuthor = playerService.findEntityById(dto.getPlayerId());
        EventInfoEntity newEventInfo = newEventInfo(event, newGoalAuthor, GOAL);
        eventInfoService.saveEventInfo(newEventInfo);
        event.setMinute(dto.getMinute());

        increasePlayerStatistic(tournament, newGoalAuthor, GOAL, protocol);

        EventDTO returnDto = repository.saveAndFlush(getEntity(event)).toDTO();

        if (dto.getAssistId() != null && dto.getAssistId() != 0) {
            PlayerEntity assistPlayer = playerService.findEntityById(dto.getAssistId());
            increasePlayerStatistic(tournament, assistPlayer, ASSIST, protocol);

            EventInfoEntity saveAssist = eventInfoService.saveEventInfo(newEventInfo(event, assistPlayer, ASSIST));
            returnDto.setAssist(saveAssist.toDTO());
        }

        return returnDto;
    }

    @Override
    @Transactional
    public EventDTO savePenalty(SaveEventDTO saveEventDTO) {
        EventEnum eventEnum = EventEnum.getEventById(saveEventDTO.getEventEnumId());

        ProtocolEntity protocol = protocolService.findEntityById(saveEventDTO.getProtocolId());
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();
        PlayerEntity player = playerService.findEntityById(saveEventDTO.getPlayerId());

        checkForRedCard(protocol, player);

        increasePlayerStatistic(tournament, player, eventEnum, protocol);
        EventEntity save = repository.save(newEvent(saveEventDTO, protocol));

        EventInfoEntity penaltyEventInfo = eventInfoService.saveEventInfo(newEventInfo(save, player, eventEnum));
        save.setEventInfo(List.of(penaltyEventInfo));

        return save.toDTO();
    }

    @Override
    public EventDTO updatePenalty(Long id, SaveEventDTO saveEventDTO) {
        EventEntity event = this.findEntityById(id);
        EventEnum eventEnum = EventEnum.getEventById(saveEventDTO.getEventEnumId());
        EventInfoEntity goalInfo = event.getEventInfoByEnum(eventEnum);

        ProtocolEntity protocol = event.getProtocol();
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        //rollback statistics and delete eventInfo
        rollBackPlayerStatistics(tournament, goalInfo.getPlayer(), EventEnum.valueOf(goalInfo.getEventName()), protocol);
        eventInfoService.delete(goalInfo.getId());

        PlayerEntity newGoalAuthor = playerService.findEntityById(saveEventDTO.getPlayerId());
        EventInfoEntity newEventInfo = newEventInfo(event, newGoalAuthor, eventEnum);
        eventInfoService.saveEventInfo(newEventInfo);
        event.setMinute(saveEventDTO.getMinute());

        increasePlayerStatistic(tournament, newGoalAuthor, eventEnum, protocol);

        EventDTO returnDto = repository.saveAndFlush(getEntity(event)).toDTO();
        return returnDto;
    }

    @Override
    @Transactional
    public EventDTO save(SaveEventDTO dto) {
        EventEnum eventEnum = EventEnum.getEventById(dto.getEventEnumId());

        ProtocolEntity protocol = protocolService.findEntityById(dto.getProtocolId());
        PlayerEntity player = playerService.findEntityById(dto.getPlayerId());
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();

        List<EventInfoEntity> events = eventInfoService.findAllEventByPlayerAndProtocol(player.getPlayerId(), protocol.getProtocolId());

        if (!events.isEmpty()) {
            EventInfoEntity eventYellowCard = getEventInfo(events, YELLOW_CARD);
            EventInfoEntity secondYellowCard = getEventInfo(events, SECOND_YELLOW_CARD);
            EventInfoEntity eventRedCard = getEventInfo(events, RED_CARD);

            if (eventRedCard == null && eventYellowCard != null && secondYellowCard == null && eventEnum == YELLOW_CARD) {
                increasePlayerStatistic(tournament, player, RED_CARD, protocol);

                EventEntity saveEvent = repository.save(newEvent(dto, protocol));
                EventInfoEntity saveEventInfo = eventInfoService.saveEventInfo(newEventInfo(saveEvent, player, SECOND_YELLOW_CARD));

                saveEvent.setEventInfo(List.of(saveEventInfo));

                return saveEvent.toDTO();
            } else if (eventRedCard != null) {
                throw EventException.build(EVENT_RED_CARD_EXCEPTION, player.getPlayerId());
            } else if (secondYellowCard != null) {
                throw EventException.build(EVENT_SECOND_YELLOW_CARD_EXCEPTION, player.getPlayerId());
            }
        }

        increasePlayerStatistic(tournament, player, eventEnum, protocol);

        EventEntity saveEvent = repository.save(newEvent(dto, protocol));
        EventInfoEntity saveEventInfo = eventInfoService.saveEventInfo(newEventInfo(saveEvent, player, eventEnum));

        saveEvent.setEventInfo(List.of(saveEventInfo));

        return saveEvent.toDTO();
    }

    @Override
    @Transactional
    public EventDTO update(Long id, SaveEventDTO dto) {
        EventEntity event = this.findEntityById(id);
        ProtocolEntity protocol = event.getProtocol();
        TournamentEntity tournament = protocol.getGame().getGroup().getTournament();
        EventInfoEntity yellowCard = event.getEventInfoByEnum(YELLOW_CARD);
        EventInfoEntity redCard = event.getEventInfoByEnum(RED_CARD);

        if (yellowCard != null) {
            rollBackPlayerStatistics(tournament, yellowCard.getPlayer(), EventEnum.valueOf(yellowCard.getEventName()), protocol);
            eventInfoService.delete(yellowCard.getId());
        } else if (redCard != null) {
            rollBackPlayerStatistics(tournament, redCard.getPlayer(), EventEnum.valueOf(redCard.getEventName()), protocol);
            eventInfoService.delete(redCard.getId());
        }

        //update new Event
        EventEnum newEventEnum = getEventById(dto.getEventEnumId());
        PlayerEntity newPlayer = playerService.findEntityById(dto.getPlayerId());

        increasePlayerStatistic(tournament, newPlayer, newEventEnum, protocol);

        EventInfoEntity newEventInfo = eventInfoService.saveEventInfo(newEventInfo(event, newPlayer, newEventEnum));
        event.setMinute(dto.getMinute());

        repository.saveAndFlush(getEntity(event));

        event.setEventInfo(List.of(newEventInfo));

        return event.toDTO();
    }


    private <T extends AbstractSaveEventDTO> EventEntity newEvent(T dto, ProtocolEntity protocol) {
        return EventEntity.builder()
                .gameScore(protocol.getTeam1Score() + ":" + protocol.getTeam2Score())
                .minute(dto.getMinute())
                .protocol(protocol)
                .build();
    }

    private EventInfoEntity newEventInfo(EventEntity event, PlayerEntity player, EventEnum eventEnum) {
        return EventInfoEntity.builder()
                .playerName(player.getName())
                .playerSurname(player.getSurname())
                .playerNumber(player.getPlayerNumber())
                .teamName(player.getTeam().getTeamName())
                .teamLogo(player.getTeam().getTeamLogo())
                .eventName(eventEnum.getEventName())
                .team(player.getTeam())
                .player(player)
                .event(event)
                .build();
    }


    private void increasePlayerStatistic(TournamentEntity tournament, PlayerEntity player, EventEnum eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(tournament, player);
        PlayerStatisticsEntity foundPlayerStat = playerStatisticsService.findEntityById(playerStatisticsEntityPK);

        log.info(foundPlayerStat.toString());

        switch (eventName) {
            case GOAL:
            case SCORE_PENALTY:
                postGoalCount(tournament, player, protocol, foundPlayerStat);
                break;
            case ASSIST:
                foundPlayerStat.setAssists(foundPlayerStat.getAssists() + 1);
                break;
            case RED_CARD:
            case SECOND_YELLOW_CARD:
                foundPlayerStat.setRedCard(foundPlayerStat.getRedCard() + 1);
                break;
            case YELLOW_CARD:
                foundPlayerStat.setYellowCard(foundPlayerStat.getYellowCard() + 1);
                break;
            default:
                break;
        }

        log.info("UPDATE PLAYER STATISTICS");
        log.info(foundPlayerStat.toString());
        playerStatisticsService.saveAndFlush(foundPlayerStat);
    }

    private void rollBackPlayerStatistics(TournamentEntity tournament, PlayerEntity player, EventEnum eventName, ProtocolEntity protocol) {
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

    private static EventEntity getEntity(EventEntity event) {
        return new EventEntity(
                event.getEventId(),
                event.getGameScore(),
                event.getMinute(),
                event.getProtocol()
        );
    }

    private static EventInfoEntity getEventInfo(List<EventInfoEntity> events, EventEnum yellowCard) {
        return events.stream()
                .filter(e -> e.getEventName().equals(yellowCard.getEventName()))
                .findFirst()
                .orElse(null);
    }


    private void checkForRedCard(ProtocolEntity protocol, PlayerEntity goalPlayer) {
        List<EventInfoEntity> events = eventInfoService.findAllEventByPlayerAndProtocol(goalPlayer.getPlayerId(), protocol.getProtocolId());
        EventInfoEntity eventRedCard = getEventInfo(events, RED_CARD);
        EventInfoEntity secondYellowCard = getEventInfo(events, SECOND_YELLOW_CARD);

        if (eventRedCard != null || secondYellowCard != null) {
            throw EventException.build(HAS_RED_CARD_EXCEPTION, goalPlayer.getPlayerId());
        }
    }
}
