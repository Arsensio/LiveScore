package com.example.livescore.service.info_upload;

import static com.example.livescore.enums.PlayerRole.*;

public final class ParserUtils {

    public final static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private ParserUtils() {
    }

    public static String beautifyName(String sheetName) throws RuntimeException {
        if (sheetName == null || sheetName.length() == 0) {
            throw new RuntimeException("Файл не должен содержать пустых полей!");
        }
        char[] nameChars = sheetName.toCharArray();
        nameChars[0] = Character.toUpperCase(nameChars[0]);
        for (int n = nameChars.length, i = 1; i < n; i++) {
            nameChars[i] = Character.toLowerCase(nameChars[i]);
        }
        return String.valueOf(nameChars);
    }

    public static String resolveRole(String roleInSheet) throws RuntimeException {
        return switch (roleInSheet) {
            case "вратарь (голкипер)" -> String.valueOf(GOALKEEPER);
            case "защитник (дефендер)" -> String.valueOf(DEFENDER);
            case "полузащитник (мидфилдер)" -> String.valueOf(MIDDLE_DEFENDER);
            case "нападающий (форвард)" -> String.valueOf(STRIKER);
            default -> throw new RuntimeException("Позиция " + roleInSheet + " не существует!\n" +
                    "Укажите позицию соответственно шаблону:\n" +
                    "вратарь (голкипер)\n" +
                    "защитник (дефендер)\n" +
                    "полузащитник (мидфилдер)\n" +
                    "нападающий (форвард)");
        };
    }

    public static String getSheetId(String url) {
        url = url.substring(url.indexOf("/d/" + 1));
        url = url.substring(3, url.indexOf("/edit"));
        return url;
    }
}
