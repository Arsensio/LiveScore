package com.example.livescore.service.event.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.*;
import com.example.livescore.repository.*;
import com.example.livescore.service.event.EventService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultEventService
        extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long, EventRepository>
        implements EventService {

    private final PlayerRepository playerRepository;
    private final ProtocolRepository protocolRepository;
    private final PlayerStatisticsRepository playerStatisticsRepository;
    private final TeamStatisticsRepository teamStatisticsRepository;
    private final GoalInfoRepository goalInfoRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultEventService(EventRepository repository, PlayerRepository playerRepository, ProtocolRepository protocolRepository, PlayerStatisticsRepository playerStatisticsRepository, TeamStatisticsRepository teamStatisticsRepository, TeamRepository teamRepository, GoalInfoRepository goalInfoRepository) {
        super(repository);
        this.playerRepository = playerRepository;
        this.protocolRepository = protocolRepository;
        this.playerStatisticsRepository = playerStatisticsRepository;
        this.teamStatisticsRepository = teamStatisticsRepository;
        this.goalInfoRepository = goalInfoRepository;
    }


    @Override
    @Transactional
    public EventDTO save(SaveGoalEventDTO dto) {
        EventNames goal = EventNames.GOAL;
        EventNames assist = EventNames.ASSIST;

        ProtocolEntity protocol = getProtocolById(dto.getProtocolId());
        GroupEntity group = protocol.getGame().getGroup();

        PlayerEntity goalPlayer = getPlayerById(dto.getPlayerId());
        updatePlayerStatistic(group, goalPlayer, goal, protocol);
        EventEntity save = repository.save(getNewEvent(dto, goal, protocol, goalPlayer));

        if (dto.getAssistId() != null) {
            PlayerEntity assistPlayer = getPlayerById(dto.getAssistId());
            updatePlayerStatistic(group, assistPlayer, assist, protocol);
            goalInfoRepository.save(getGoalInfoEntity(assistPlayer, save, 2));
        } else if (dto.getIsPenalty()) {
            goalInfoRepository.save(getGoalInfoEntity(null, save, 5));
        }

        log.info("CREATE NEW EVENT {}", save);

        return save.toDTO();
    }

    @Override
    @Transactional
    public EventDTO save(SaveEventDTO dto) {
        EventNames event = EventNames.getEventById(dto.getEventEnumId());

        ProtocolEntity protocol = getProtocolById(dto.getProtocolId());
        PlayerEntity player = getPlayerById(dto.getPlayerId());
        GroupEntity group = protocol
                .getGame()
                .getGroup();

        updatePlayerStatistic(group, player, event, protocol);
        EventEntity save = repository.save(getNewEvent(dto, event, protocol, player));
        log.info("CREATE NEW EVENT {}", save);

        return save.toDTO();
    }

    private GoalInfoEntity getGoalInfoEntity(PlayerEntity assistPlayer, EventEntity event, long eventId) {
        return new GoalInfoEntity(assistPlayer, EventNames.getEventNameById(eventId), event);
    }

    private EventEntity getNewEvent(SaveEventDTO dto, EventNames event, ProtocolEntity protocol, PlayerEntity player) {
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

    private ProtocolEntity getProtocolById(long id) {
        Optional<ProtocolEntity> protocol = protocolRepository.findById(id);
        if (protocol.isEmpty()) {
            throw ResourceNotFoundException.build(id, "ProtocolEntity");
        } else {
            return protocol.get();
        }
    }

    private PlayerEntity getPlayerById(long id) {
        Optional<PlayerEntity> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw ResourceNotFoundException.build(id, "PlayerEntity");
        } else {
            return player.get();
        }
    }

    private void updatePlayerStatistic(GroupEntity group, PlayerEntity player, EventNames eventName, ProtocolEntity protocol) {
        PlayerStatisticsEntityPK playerStatisticsEntityPK = new PlayerStatisticsEntityPK(group, player);
        Optional<PlayerStatisticsEntity> foundPlayer = playerStatisticsRepository.findById(playerStatisticsEntityPK);

        if (foundPlayer.isEmpty()) {
            throw ResourceNotFoundException.build(player.getPlayerId(), "PlayerEntity");
        } else {
            PlayerStatisticsEntity foundPlayerStat = foundPlayer.get();
            log.info(foundPlayerStat.toString());
            if (eventName == EventNames.GOAL) {
                increaseAndDecreaseGoalCount(group, player, protocol, foundPlayerStat);
            } else if (eventName == EventNames.ASSIST) {
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
            playerStatisticsRepository.saveAndFlush(foundPlayerStat);
        }
    }

    private void increaseAndDecreaseGoalCount(GroupEntity group, PlayerEntity player, ProtocolEntity protocol, PlayerStatisticsEntity foundPlayerStat) {
        foundPlayerStat.setGoals(foundPlayerStat.getGoals() + 1);
        TeamEntity team1 = protocol.getTeam1();
        TeamEntity team2 = protocol.getTeam2();

        TeamStatisticsEntityPK teamStatisticsEntityPK;
        if (player.getTeam().equals(team1)) {
            teamStatisticsEntityPK = new TeamStatisticsEntityPK(group, team1);
            teamStatisticsRepository.incrementGoalCount(teamStatisticsEntityPK);
            TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(group, team2);
            teamStatisticsRepository.incrementGoalMissedCount(team2StatisticsEntityPK);
            protocol.setTeam1Score(protocol.getTeam1Score() + 1);

        } else {
            teamStatisticsEntityPK = new TeamStatisticsEntityPK(group, team2);
            teamStatisticsRepository.incrementGoalCount(teamStatisticsEntityPK);
            TeamStatisticsEntityPK team2StatisticsEntityPK = new TeamStatisticsEntityPK(group, team1);
            teamStatisticsRepository.incrementGoalMissedCount(team2StatisticsEntityPK);
            protocol.setTeam2Score(protocol.getTeam2Score() + 1);
        }
        protocolRepository.saveAndFlush(protocol);
        log.info("UPDATE PROTOCOL SCORE " + protocol.getTeam1() + " " + protocol.getTeam1Score() + " : " + protocol.getTeam2Score() + " " + protocol.getTeam2());
    }

}
