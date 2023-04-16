package com.example.livescore.controllers.info_upload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TournamentInformationController {

    ResponseEntity<String> uploadPlayerInfoFile(MultipartFile file, Long tournamentId);

    ResponseEntity<String> uploadPlayerInfoLink(String link, Long tournamentId);
}
