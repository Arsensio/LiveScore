package com.example.livescore.service.info_parser;

import com.example.livescore.service.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerInfoParserService {

    private final PlayerService playerService;
}
