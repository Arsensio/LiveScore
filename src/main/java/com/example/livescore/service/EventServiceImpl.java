package com.example.livescore.service;

import com.example.livescore.store.EventRepository;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.store.ProtocolRepository;
import com.example.livescore.store.TeamStatisticsRepository;
import com.example.livescore2.models.EventEntity;
import com.example.livescore2.models.ProtocolEntity;
import com.example.livescore2.models.TeamStatisticsEntityPK;
import com.example.livescore2.web.events.EventDTO;
import com.example.livescore2.web.events.SaveEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;
    private final ProtocolRepository protocolRepository;
    private final TeamStatisticsRepository teamStatisticsRepository;

    @Override
    public List<EventDTO> getAll(Long protocolId) {
        return eventRepository.getEventEntitiesByProtocolId(protocolId).stream().map(EventEntity::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDTO postIndividual(SaveEventDTO saveEventDTO) {
        if (saveEventDTO.getEventEnumId() == 1) setScore(saveEventDTO);

        return eventRepository.save(
                new EventEntity(
                        null,
                        protocolRepository.getReferenceById(saveEventDTO.getProtocolId()),
                        EventNames.getEventById(saveEventDTO.getEventEnumId()).toString(), // мы посылаем цифру, потом конвертируем его в стринг, зачем?, исправить потом
                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
                        saveEventDTO.getMinute()
                )
        ).toDTO();
    }

    @Override
    public EventDTO putIndividual(Long id, SaveEventDTO saveEventDTO) {
        if (saveEventDTO.getEventEnumId() == 1) setScore(saveEventDTO);

        return eventRepository.save(
                new EventEntity(
                        id,
                        protocolRepository.getReferenceById(saveEventDTO.getProtocolId()),
                        EventNames.getEventById(saveEventDTO.getEventEnumId()).toString(),
                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
                        saveEventDTO.getMinute()
                )
        ).toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        eventRepository.deleteById(id);
    }

    private void setScore(SaveEventDTO saveEventDTO) {
        ProtocolEntity pE = protocolRepository.getReferenceById(saveEventDTO.getProtocolId());
        boolean first = playerRepository.getReferenceById(saveEventDTO.getPlayerId()).getTeam().equals(pE.getTeam1());
        pE.incrementTeamScore(first);

        TeamStatisticsEntityPK firstTeamStatistics = new TeamStatisticsEntityPK(
                protocolRepository.getReferenceById(saveEventDTO.getProtocolId()).getGame().getGroup(),
                protocolRepository.getReferenceById(saveEventDTO.getProtocolId()).getTeam1()
        );

        TeamStatisticsEntityPK secondTeamStatistics = new TeamStatisticsEntityPK(
                protocolRepository.getReferenceById(saveEventDTO.getProtocolId()).getGame().getGroup(),
                protocolRepository.getReferenceById(saveEventDTO.getProtocolId()).getTeam2()
        );

        if (first) {
            teamStatisticsRepository.incrementGoalCount(firstTeamStatistics);
            teamStatisticsRepository.incrementGoalMissedCount(secondTeamStatistics);
        } else {
            teamStatisticsRepository.incrementGoalCount(secondTeamStatistics);
            teamStatisticsRepository.incrementGoalMissedCount(firstTeamStatistics);
        }

    }


    // ========= test =========
    private void testTeamStatistics(SaveEventDTO saveEventDTO) {
        TeamStatisticsEntityPK tspk = new TeamStatisticsEntityPK(
                protocolRepository.getReferenceById(saveEventDTO.getProtocolId()).getGame().getGroup(),
                playerRepository.getReferenceById(saveEventDTO.getPlayerId()).getTeam()
        );

        teamStatisticsRepository.incrementWinCount(tspk);
        teamStatisticsRepository.incrementDrawCount(tspk);
        teamStatisticsRepository.incrementLoseCount(tspk);

    }
    // ========= test =========
}
