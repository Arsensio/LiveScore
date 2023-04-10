package com.example.livescore.service.info_upload;

import java.io.IOException;
import java.io.InputStream;

public interface PlayerInfoParserService {

    String savePlayers(InputStream inputStream) throws IOException, RuntimeException;

    String savePlayers(String url) throws IOException, RuntimeException;
}
