package com.example.livescore.web.players;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlayerDTO {

    private Long playerId;
    private Long newTeamId;
}
