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
public class GameServiceImpl implements MainService<SaveGameDTO, GameDTO>{

    private final GameRepository gameRepository;
    private final GroupRepository groupRepository;
    private final ProtocolRepository protocolRepository;

    @Override
    public List<GameDTO> getAll() {
        return gameRepository.findAll().stream().map(GameEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public GameDTO getIndividual(Long id) {
        return gameRepository.getReferenceById(id).toDTO();
    }


    // протокол создается после создания игры, поэтому передавать айди не получиться, продумать этот момент
    // найти более оптимальный способ хранения группы, нежели чем постоянно передавать
    /** игра создается до того как будет сыграна, поэтому:
     * isPlayed = false
     * */
    @Override
    public GameDTO postIndividual(SaveGameDTO saveGameDTO) {
//        return gameRepository.save(
//                new GameEntity(
//                        null,
//                        false,
//                        groupRepository.getReferenceById(saveGameDTO.getGroupId()),
//                        protocolRepository.getReferenceById(saveGameDTO.getProtocol())
//                )
//        ).toDTO();
        return null;

    }

    @Override
    public GameDTO putIndividual(Long id, SaveGameDTO saveGameDTO) {
//        return gameRepository.save(
//                new GameEntity(
//                        id,
//                        saveGameDTO.getIsPlayed(),
//                        "",
//                        groupRepository.getReferenceById(saveGameDTO.getGroupId()),
//                        protocolRepository.getReferenceById(saveGameDTO.getProtocol())
//                )
//        ).toDTO();
        return null;

    }

    @Override
    public void deleteIndividual(Long id) {

    }
}
