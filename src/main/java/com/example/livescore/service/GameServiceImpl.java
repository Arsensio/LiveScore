package com.example.livescore.service;

import com.example.livescore.models.GameEntity;
import com.example.livescore.store.GameRepository;
import com.example.livescore.store.GroupRepository;
import com.example.livescore.store.ProtocolRepository;
import com.example.livescore.web.games.GameDTO;
import com.example.livescore.web.games.SaveGameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final GroupRepository groupRepository;
    private final ProtocolRepository protocolRepository;

    @Override
    public List<GameDTO> getAllGamesOfGroup(Long groupId) {
        return gameRepository.getGameEntitiesByGroupGroupId(groupId).stream().map(GameEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public GameDTO getIndividual(Long id) {
        return gameRepository.getReferenceById(id).toDTO();
    }


    /**
     *      игра создается до того как будет сыграна, поэтому:
     *      isPlayed = false
     *      protocol = null
     * */
    @Override
    public GameDTO postIndividual(SaveGameDTO saveGameDTO) {
        return gameRepository.save(
                new GameEntity(
                        null,
                        false,
                        groupRepository.getReferenceById(saveGameDTO.getGroupId()),
                        null
                )
        ).toDTO();
    }


    @Override
    public GameDTO putIndividual(Long id, SaveGameDTO saveGameDTO) {
        System.out.println("============================================================");
        System.out.println(saveGameDTO.isPlayed());
        System.out.println("============================================================");


        return gameRepository.save(
                new GameEntity(
                        id,
                        saveGameDTO.isPlayed(),
                        groupRepository.getReferenceById(saveGameDTO.getGroupId()),
                        protocolRepository.getReferenceById(saveGameDTO.getProtocolId())
                )
        ).toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        gameRepository.deleteById(id);
    }
}
