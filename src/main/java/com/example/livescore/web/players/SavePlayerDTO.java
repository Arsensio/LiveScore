package com.example.livescore.web.players;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePlayerDTO {

    private Long teamId;
    private String name;
    private String surname;
    private Integer playerNumber;
    private String role;
    private Long tournamentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavePlayerDTO playerDTO = (SavePlayerDTO) o;
        return Objects.equals(name, playerDTO.name)
                && Objects.equals(surname, playerDTO.surname)
                && Objects.equals(playerNumber, playerDTO.playerNumber);
    }

    @Override
    public String toString() {
        return "Игрок{" +
                "имя='" + name + '\'' +
                ", фамилия='" + surname + '\'' +
                ", номер=" + playerNumber +
                ", позиция='" + role + '\'' +
                '}';
    }

    public boolean isThereNullFields() {
        return name == null
                || surname == null
                || playerNumber == null
                || role == null;
    }
}
