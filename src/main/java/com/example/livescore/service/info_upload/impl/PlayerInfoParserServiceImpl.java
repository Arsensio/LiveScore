package com.example.livescore.service.info_upload.impl;

import com.example.core.exception.exceptions.EqualPlayersException;
import com.example.core.exception.exceptions.PlayerNullFieldsException;
import com.example.livescore.service.info_upload.GoogleSheets;
import com.example.livescore.service.info_upload.PlayerInfoParserService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
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
    private static final String SUCCESS_MESSAGE = "Все игроки успешно сохранены!";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final PlayerService playerService;
    private final TeamFootballService teamService;

    @Override
    public String saveTeamsAndPlayers(InputStream inputStream, Long tournamentId) throws IOException, RuntimeException {
//        List<SavePlayerDTO> players = excelToPlayers(inputStream, tournamentId);
//        save(players);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String saveTeamsAndPlayers(String link, Long tournamentId) throws IOException, RuntimeException, GeneralSecurityException {
//        List<SavePlayerDTO> players = googleSheetToPlayers(link, tournamentId);
//        save(players);
        return SUCCESS_MESSAGE;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }


    public void googleSheetToTeamsAndPlayers(String link) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = getSheetId(link);
            List<SavePlayerDTO> players = new ArrayList<>();
            List<SaveTeamDTO> teams = new ArrayList<>();

            Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleSheets.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            String title = sheets.spreadsheets()
                    .get(spreadsheetId)
                    .execute()
                    .getSheets()
                    .get(0)
                    .getProperties()
                    .getTitle();

            String range = title + "!A2:D";

            ValueRange response = sheets.spreadsheets()
                    .values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();

            System.out.println("Vyzvalsya: " + spreadsheetId);

            if (values == null || values.isEmpty()) {
                throw new RuntimeException("Данные отсутствуют в таблице, или таблица пуста!");
            } else {
                for (List row : values) {
                    log.info("Row value: Time {}", row.get(0).toString());
                    log.info("Row value: team {}", row.get(1).toString());
                    log.info("Row value: team logo {}", row.get(2).toString());
                    log.info("Row value: players link {}", row.get(3).toString());

                    System.out.println("1");
                    Sheets playerSheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleSheets.getCredentials(HTTP_TRANSPORT))
                            .setApplicationName(APPLICATION_NAME)
                            .build();
                    System.out.println("2 " + getDriveId(row.get(3).toString()));
                    String playerSheetTitle = playerSheets.spreadsheets()
                            .get(getDriveId(row.get(3).toString()))
                            .execute()
                            .getSheets()
                            .get(0)
                            .getProperties()
                            .getTitle();
                    System.out.println("3 " + playerSheetTitle);
                    String playerrange = playerSheetTitle + "!A2:D";
                    System.out.println("4 " + playerrange);
                    ValueRange playerresponse = playerSheets.spreadsheets()
                            .values()
                            .get(getDriveId(row.get(3).toString()), playerrange)
                            .execute();
                    System.out.println("5");
                    List<List<Object>> playervalues = playerresponse.getValues();
                    System.out.println("6");
                    System.out.println("drive id: " + getDriveId(row.get(3).toString()));
                    System.out.println("7");
                    for (List row2 : playervalues) {
                        log.info("Player name: {}", row2.get(0).toString());
                        log.info("Player surname: {}", row2.get(1).toString());
                        log.info("Player number: {}", row2.get(2).toString());
                        log.info("Player position: {}", row2.get(3).toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<SavePlayerDTO> googleSheetToPlayers(String link, Long tournamentId) throws GeneralSecurityException,
            IOException, RuntimeException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = getSheetId(link);
        List<SavePlayerDTO> players = new ArrayList<>();
        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleSheets.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String title = sheets.spreadsheets()
                .get(spreadsheetId)
                .execute()
                .getSheets()
                .get(0)
                .getProperties()
                .getTitle();

        String range = title + "!A2:D";  // захардкоденный вариант, может поменятся

        ValueRange response = sheets.spreadsheets()
                .values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            throw new RuntimeException("Данные отсутствуют в таблице, или таблица пуста!");
        } else {
            for (List row : values) {
                SavePlayerDTO playerDTO = SavePlayerDTO.builder()
                        .name(
                                beautifyName(row.get(1).toString())
                        )
                        .surname(
                                beautifyName(row.get(2).toString())
                        )
                        .teamId(
                                getTeamIdOrThrowNull(
                                        row.get(3).toString(), tournamentId
                                )
                        )
                        .playerNumber(
                                Integer.parseInt(row.get(4).toString())
                        )
                        .role(
                                resolveRole(row.get(5).toString())
                        )
                        .build();
                if (alreadyPresents(playerDTO, players)) {
                    throw EqualPlayersException.withEqualPlayersData(playerDTO);
                }
                if (playerDTO.isThereNullFields()) {
                    throw PlayerNullFieldsException.withPlayerData(playerDTO);
                }

                playerService.checkPlayerNumberForExistence(playerDTO.getPlayerNumber(), playerDTO.getTeamId());
                playerDTO.setTournamentId(tournamentId);
                players.add(playerDTO);
            }
        }
        return players;
    }


    /**
     * This method will work only with certain format of an Excel file, any changes to file structure may cause errors
     */
    private List<SavePlayerDTO> excelToPlayers(InputStream inputStream, Long tournamentId) throws IOException,
            RuntimeException {
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
                    case 1 -> playerDTO.setName(
                            beautifyName(currentCell.getStringCellValue())
                    );
                    case 2 -> playerDTO.setSurname(
                            beautifyName(currentCell.getStringCellValue())
                    );
                    case 3 -> playerDTO.setTeamId(
                            getTeamIdOrThrowNull(
                                    currentCell.getStringCellValue(), tournamentId
                            )
                    );
                    case 4 -> playerDTO.setPlayerNumber(
                            (int) currentCell.getNumericCellValue()
                    );
                    case 5 -> playerDTO.setRole(
                            resolveRole(currentCell.getStringCellValue())
                    );
                }
                cellId++;
            }
            if (alreadyPresents(playerDTO, players)) {
                throw EqualPlayersException.withEqualPlayersData(playerDTO);
            }
            if (playerDTO.isThereNullFields()) {
                throw PlayerNullFieldsException.withPlayerData(playerDTO);
            }

            playerService.checkPlayerNumberForExistence(playerDTO.getPlayerNumber(), playerDTO.getTeamId());
            playerDTO.setTournamentId(tournamentId);
            players.add(playerDTO);
        }
        workbook.close();
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

    // todo: искать команды по турнаменту, не только по названиям
    private Long getTeamIdOrThrowNull(String teamName, Long tournamentId) {
        return teamService.findTeamByNameInTournament(teamName, tournamentId).getTeamId();
    }

    private class Builder {
        public Builder(NetHttpTransport netHttpTransport, GsonFactory defaultInstance, HttpRequestInitializer requestInitializer) {
        }
    }
}
