package com.example.livescore.service.info_upload.impl;

import com.example.core.exception.exceptions.EqualPlayersException;
import com.example.core.exception.exceptions.PlayerNullFieldsException;
import com.example.livescore.service.info_upload.GoogleApi;
import com.example.livescore.service.info_upload.PlayerInfoParserService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.players.SavePlayerDTO;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.livescore.service.info_upload.ParserUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerInfoParserServiceImpl implements PlayerInfoParserService {

    private static final String APPLICATION_NAME = "SDUFootballLive";
    private static final String SUCCESS_MESSAGE = "Все команды и игроки успешно сохранены!";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final PlayerService playerService;
    private final TeamFootballService teamService;

    public static boolean hasExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }

    @Override
    public String saveTeamsAndPlayers(InputStream inputStream, Long tournamentId) throws IOException, RuntimeException {
//        List<SavePlayerDTO> players = excelToPlayers(inputStream, tournamentId);
//        save(players);
        return "Функционал переписан, пожалуйста воспользуйтесь загрузкой через Google Sheets";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveTeamsAndPlayers(String link, Long tournamentId) throws IOException, RuntimeException, GeneralSecurityException {
        googleSheetToTeamsAndPlayers(link, tournamentId);
        return SUCCESS_MESSAGE;
    }

    // todo: добавить: если не сохранил одного, пусть не сохраняет никого
    public void googleSheetToTeamsAndPlayers(String link, Long tournamentId) throws GeneralSecurityException, IOException {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String teamsSpreadsheetId = getSheetId(link);

            Sheets teamsSheet = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleApi.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            String teamsSheetTitle = teamsSheet.spreadsheets()
                    .get(teamsSpreadsheetId)
                    .execute()
                    .getSheets()
                    .get(0)
                    .getProperties()
                    .getTitle();

            String teamsSheetRange = teamsSheetTitle + "!A2:D";

            ValueRange teamsSheetValueRange = teamsSheet.spreadsheets()
                    .values()
                    .get(teamsSpreadsheetId, teamsSheetRange)
                    .execute();

            List<List<Object>> teamsSheetValues = teamsSheetValueRange.getValues();

            if (teamsSheetValues == null || teamsSheetValues.isEmpty()) {
                throw new RuntimeException("Данные отсутствуют в таблице, или таблица пуста!");
            } else {
                for (List teamsSheetRow : teamsSheetValues) {
                    SaveTeamDTO saveTeamDTO = SaveTeamDTO.builder()
                            .teamName(teamsSheetRow.get(1).toString())
                            .teamLogo(teamsSheetRow.get(2).toString())
                            .tournamentId(tournamentId)
                            .build();

                    Long teamId = teamService.save(saveTeamDTO).getTeamId();

                    log.info("Row value: Time {}", teamsSheetRow.get(0).toString());
                    log.info("Row value: team {}", teamsSheetRow.get(1).toString());
                    log.info("Row value: team logo {}", teamsSheetRow.get(2).toString());
                    log.info("Row value: players link {}", teamsSheetRow.get(3).toString());

                    savePlayers(googleSheetToPlayers(teamsSheetRow.get(3).toString(), tournamentId, teamId));
                }
            }
    }

    private List<SavePlayerDTO> googleSheetToPlayers(String link, Long tournamentId, Long teamId) throws GeneralSecurityException,
            IOException, RuntimeException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = getSheetId(link);
        List<SavePlayerDTO> players = new ArrayList<>();

        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleApi.getCredentials(HTTP_TRANSPORT))
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
                                beautifyName(row.get(0).toString())
                        )
                        .surname(
                                beautifyName(row.get(1).toString())
                        )
//                        .teamId(
//                                getTeamIdOrThrowNull(
//                                        row.get(3).toString(), tournamentId
//                                )
//                        )
                        .playerNumber(
                                Integer.parseInt(row.get(2).toString())
                        )
                        .role(
                                resolveRole(row.get(3).toString())
                        )
                        .build();

                if (alreadyPresents(playerDTO, players)) {
                    throw EqualPlayersException.withEqualPlayersData(playerDTO);
                }
                if (playerDTO.isThereNullFields()) {
                    throw PlayerNullFieldsException.withPlayerData(playerDTO);
                }

//                playerService.checkPlayerNumberForExistence(playerDTO.getPlayerNumber(), playerDTO.getTeamId());
                playerDTO.setTournamentId(tournamentId);
                playerDTO.setTeamId(teamId);
                log.info("sheet to players: SavePlayerDto: {}", playerDTO);
                players.add(playerDTO);
            }
        }
        return players;
    }


    /**
     * This method will work only with certain format of an Excel file, any changes to file structure may cause errors
     */
//    private List<SavePlayerDTO> excelToPlayers(InputStream inputStream, Long tournamentId) throws IOException,
//            RuntimeException {
//        List<SavePlayerDTO> players = new ArrayList<>();
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rows = sheet.iterator();
//        if (rows.hasNext()) {
//            rows.next(); // skipping the first header line
//        }
//        while (rows.hasNext()) {
//            Row currentRow = rows.next();
//            Iterator<Cell> cellsInRow = currentRow.iterator();
//            SavePlayerDTO playerDTO = new SavePlayerDTO();
//
//            int cellId = 0;
//            while (cellsInRow.hasNext()) {
//                Cell currentCell = cellsInRow.next();
//                switch (cellId) {
//                    case 1 -> playerDTO.setName(
//                            beautifyName(currentCell.getStringCellValue())
//                    );
//                    case 2 -> playerDTO.setSurname(
//                            beautifyName(currentCell.getStringCellValue())
//                    );
//                    case 3 -> playerDTO.setTeamId(
//                            getTeamIdOrThrowNull(
//                                    currentCell.getStringCellValue(), tournamentId
//                            )
//                    );
//                    case 4 -> playerDTO.setPlayerNumber(
//                            (int) currentCell.getNumericCellValue()
//                    );
//                    case 5 -> playerDTO.setRole(
//                            resolveRole(currentCell.getStringCellValue())
//                    );
//                }
//                cellId++;
//            }
//            if (alreadyPresents(playerDTO, players)) {
//                throw EqualPlayersException.withEqualPlayersData(playerDTO);
//            }
//            if (playerDTO.isThereNullFields()) {
//                throw PlayerNullFieldsException.withPlayerData(playerDTO);
//            }
//
//            playerService.checkPlayerNumberForExistence(playerDTO.getPlayerNumber(), playerDTO.getTeamId());
//            playerDTO.setTournamentId(tournamentId);
//            players.add(playerDTO);
//        }
//        workbook.close();
//        return players;
//    }
    private void savePlayers(List<SavePlayerDTO> players) {
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

    private Long getTeamIdOrThrowNull(String teamName, Long tournamentId) {
        return teamService.findTeamByNameInTournament(teamName, tournamentId).getTeamId();
    }
}
