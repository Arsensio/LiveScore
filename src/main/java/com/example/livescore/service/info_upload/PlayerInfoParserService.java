package com.example.livescore.service.info_upload;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public interface PlayerInfoParserService {

    String saveTeamsAndPlayers(InputStream inputStream, Long tournamentId) throws IOException, RuntimeException;

    String saveTeamsAndPlayers(String link, Long tournamentId) throws IOException, RuntimeException, GeneralSecurityException;
}
