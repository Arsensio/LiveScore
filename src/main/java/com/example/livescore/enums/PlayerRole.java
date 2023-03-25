package com.example.livescore.enums;

public enum PlayerRole {
    GOALKEEPER("вратарь (голкипер)"),
    DEFENDER("защитник (дефендер)"),
    MIDDLE_DEFENDER("полузащитник (мидфилдер)"),
    STRIKER("нападающий (форвард)");

    private String description;

    PlayerRole(String description) {

    }

    public String description() {
        return this.description;
    }
}
