package com.example.livescore.service.info_upload.impl;

import com.example.livescore.service.info_upload.PlayerInfoParserService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.players.SavePlayerDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.example.livescore.service.info_upload.ParserUtils.*;

@Service
@RequiredArgsConstructor
public class PlayerInfoParserServiceImpl implements PlayerInfoParserService {

    private final PlayerService playerService;
    private final TeamFootballService teamService;

    @Override
    public void savePlayers(InputStream inputStream) {
        List<SavePlayerDTO> players = excelToPlayers(inputStream);
        for (SavePlayerDTO playerDTO : players) {
            playerService.save(playerDTO);
        }
    }

    @Override
    public void savePlayers(String url) {

    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }

    /**
     * This method will work only with certain format of an Excel file, any changes to file structure may cause errors
     */
    private List<SavePlayerDTO> excelToPlayers(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            Iterator<Row> rows = sheet.iterator();
            List<SavePlayerDTO> players = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) { // skipping first row with headers
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                SavePlayerDTO playerDTO = new SavePlayerDTO();

                int cellId = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellId) {
                        case 1 -> playerDTO.setName(beautifyName(currentCell.getStringCellValue()));
                        case 2 -> playerDTO.setSurname(beautifyName(currentCell.getStringCellValue()));
                        case 3 -> playerDTO.setTeamId(
                                teamService.findTeamByName(
                                        beautifyName(currentCell.getStringCellValue())
                                ).getTeamId());
                        case 4 -> playerDTO.setPlayerNumber((int) currentCell.getNumericCellValue());
                        case 5 -> playerDTO.setRole(resolveRole(currentCell.getStringCellValue()));
                    }
                    cellId++;
                }
                players.add(playerDTO);
            }
            workbook.close();
            return players;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
