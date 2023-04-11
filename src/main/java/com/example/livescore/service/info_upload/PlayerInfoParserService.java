package com.example.livescore.service.info_upload;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public interface PlayerInfoParserService {

    String savePlayers(InputStream inputStream) throws IOException, RuntimeException;

    String savePlayers(String url) throws IOException, RuntimeException, GeneralSecurityException;
}
