package com.example.livescore.controllers.info;

import com.example.livescore.service.info.PlayerInfoParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.livescore.service.info.PlayerInfoParserService.hasExcelFormat;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class TournamentInformationController {

    private final PlayerInfoParserService playerInfoParserService;

    @PostMapping("/upload/playerInfo")
    public ResponseEntity<String> uploadPlayerInfo(@RequestParam("file") MultipartFile file) {
        if (!hasExcelFormat(file)) {
            return ResponseEntity.badRequest().body("Please upload an excel (.xlsx) format!");
        }

        try {
            playerInfoParserService.savePlayers(file.getInputStream());
            return ResponseEntity.ok().body("Successfully saved all players!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("An error happened while parsing excel file, " +
                    "please make sure it don't contain errors!");
        }
    }
}
