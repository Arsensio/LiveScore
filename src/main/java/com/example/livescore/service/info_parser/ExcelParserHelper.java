package com.example.livescore.service.info_parser;

import com.example.livescore.web.players.SavePlayerDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelParserHelper {

    public static String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static String[] headers = {"Timestamp", "Имя", "Фамилия", "За какую команду вы играете?", "Ваш номер",
            "Ваша позиция (роль)"};
    private static String sheetName = "Данные игроков футбольного турнира";

    public static boolean hasExcelFormat(MultipartFile file) {
        return file.getContentType().equals(type);
    }

    public static List<SavePlayerDTO> excelToPlayers(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            Iterator<Row> rows = sheet.iterator();

            List<SavePlayerDTO> players = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    continue;
                }

//                Iterator<Cell> cellIterator =
            }


        } catch (IOException e) {

        }
        return null;
    }
}
