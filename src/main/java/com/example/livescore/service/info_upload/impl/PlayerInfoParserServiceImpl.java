package com.example.livescore.service.info_upload.impl;

import com.example.core.exception.exceptions.EqualPlayersException;
import com.example.core.exception.exceptions.PlayerNullFieldsException;
import com.example.livescore.service.info_upload.GoogleSheets;
import com.example.livescore.service.info_upload.PlayerInfoParserService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.players.SavePlayerDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.example.livescore.service.info_upload.ParserUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerInfoParserServiceImpl implements PlayerInfoParserService {

    private static final String APPLICATION_NAME = "SDUFootballLive";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final PlayerService playerService;
    private final TeamFootballService teamService;

    @Override
    public String savePlayers(InputStream inputStream) throws IOException, RuntimeException {
        List<SavePlayerDTO> players = excelToPlayers(inputStream);
        save(players);
        return "Successfully saved all players!";
    }

    @Override
    public String savePlayers(String url) throws IOException, RuntimeException, GeneralSecurityException {
        List<SavePlayerDTO> players = googleSheetToPlayers(url);
        save(players);
        return "Successfully saved all players!";
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }

    /**
     * This method will work only with certain format of an Excel file, any changes to file structure may cause errors
     */
    private List<SavePlayerDTO> excelToPlayers(InputStream inputStream) throws IOException, RuntimeException {
        List<SavePlayerDTO> players = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        if (rows.hasNext()) {
            rows.next(); // skipping the first header line
        }
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            SavePlayerDTO playerDTO = new SavePlayerDTO();

            int cellId = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
                switch (cellId) {
                    case 1 -> playerDTO.setName(beautifyName(currentCell.getStringCellValue()));
                    case 2 -> playerDTO.setSurname(beautifyName(currentCell.getStringCellValue()));
                    case 3 -> playerDTO.setTeamId(getTeamIdOrThrowNull(
                                    beautifyName(currentCell.getStringCellValue())
                            )
                    );
                    case 4 -> playerDTO.setPlayerNumber((int) currentCell.getNumericCellValue());
                    case 5 -> playerDTO.setRole(resolveRole(currentCell.getStringCellValue()));
                }
                cellId++;
            }
            if (alreadyPresents(playerDTO, players)) {
                throw EqualPlayersException.withEqualPlayersData(playerDTO);
            }
            if (playerDTO.isThereNullFields()) {
                throw PlayerNullFieldsException.withPlayerData(playerDTO);
            }
            players.add(playerDTO);
        }
        workbook.close();
        return players;
    }

    public List<SavePlayerDTO> googleSheetToPlayers(String url) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = getSheetId(url);
        final String range = "Form Responses 1!A2:F";  // todo: захардкоденный шит нейм, поменять на гет чтоль
        List<SavePlayerDTO> players = new ArrayList<>();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleSheets.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets()
                .values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            log.info("No data found.");
        } else {
            for (List row : values) {
                SavePlayerDTO playerDTO = SavePlayerDTO.builder()
                        .name(beautifyName(row.get(1).toString()))
                        .surname(beautifyName(row.get(2).toString()))
                        .teamId(getTeamIdOrThrowNull(beautifyName(row.get(3).toString())))
                        .playerNumber(Integer.parseInt(row.get(4).toString()))
                        .role(resolveRole(row.get(5).toString()))
                        .build();
                if (alreadyPresents(playerDTO, players)) {
                    throw EqualPlayersException.withEqualPlayersData(playerDTO);
                }
                if (playerDTO.isThereNullFields()) {
                    throw PlayerNullFieldsException.withPlayerData(playerDTO);
                }
                players.add(playerDTO);
            }
        }
        return players;
    }

    private void save(List<SavePlayerDTO> players) {
        for (SavePlayerDTO playerDTO : players) {
            playerService.save(playerDTO);
        }
    }

    private boolean alreadyPresents(SavePlayerDTO playerDTO, List<SavePlayerDTO> playerList) {
        for (SavePlayerDTO player : playerList) {
            if (player.equals(playerDTO)) {
                return true;
            }
        }
        return false;
    }

    private Long getTeamIdOrThrowNull(String teamName) {
        return teamService.findTeamByName(teamName).getTeamId();
    }
}
