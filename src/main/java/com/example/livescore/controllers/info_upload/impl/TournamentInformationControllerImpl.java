package com.example.livescore.controllers.info_upload.impl;

import com.example.livescore.controllers.info_upload.TournamentInformationController;
import com.example.livescore.service.info_upload.GoogleSheets;
import com.example.livescore.service.info_upload.PlayerInfoParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.example.livescore.service.info_upload.impl.PlayerInfoParserServiceImpl.hasExcelFormat;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class TournamentInformationControllerImpl implements TournamentInformationController {

    private final PlayerInfoParserService playerInfoParserService;
    private final GoogleSheets googleSheets;

    @Override
    @PostMapping("/upload/player_info/file")
    public ResponseEntity<String> uploadPlayerInfoFile(@RequestParam("file") MultipartFile file) {
        if (!hasExcelFormat(file)) {
            return ResponseEntity.badRequest().body("Please upload an excel (.xlsx) format!");
        }

        try {
            return ResponseEntity.ok()
                    .body(
                            playerInfoParserService.savePlayers(file.getInputStream())
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
    public ResponseEntity<String> uploadPlayerInfoLink(@RequestParam("url") String url) {
        try {
            return ResponseEntity.ok()
                    .body(
                            playerInfoParserService.savePlayers(url)
                    );
        } catch (GeneralSecurityException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("An error happened while parsing excel file, please make sure it don't contain errors!");
        }
    }
}
