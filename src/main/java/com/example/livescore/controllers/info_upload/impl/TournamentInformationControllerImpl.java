package com.example.livescore.controllers.info_upload.impl;

import com.example.livescore.controllers.info_upload.TournamentInformationController;
import com.example.livescore.service.info_upload.impl.PlayerInfoParserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.example.livescore.service.info_upload.impl.PlayerInfoParserServiceImpl.hasExcelFormat;

@Slf4j
@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class TournamentInformationControllerImpl implements TournamentInformationController {

    private final PlayerInfoParserServiceImpl playerInfoParserService;

    @Override
    @PostMapping("/upload/player_info/file")
    public ResponseEntity<String> uploadPlayerInfoFile(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("tournamentId") Long tournamentId) {
        if (!hasExcelFormat(file)) {
            return ResponseEntity.badRequest().body("Please upload an excel (.xlsx) format!");
        }

        try {
            return ResponseEntity.ok()
                    .body(
                            playerInfoParserService.saveTeamsAndPlayers(file.getInputStream(), tournamentId)
                    );
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("An error happened while parsing excel file, please make sure it don't contain errors!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @Override
    @PostMapping("/upload/player_info/link")
    public ResponseEntity<String> uploadPlayerInfoLink(@RequestParam("link") String link,
                                                       @RequestParam("tournamentId") Long tournamentId) {
        try {
            return ResponseEntity.ok()
                    .body(
                            playerInfoParserService.saveTeamsAndPlayers(link, tournamentId)
                    );
        } catch (GeneralSecurityException | RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("An error happened while parsing excel file, please make sure it don't contain errors!");
        }
    }
}
