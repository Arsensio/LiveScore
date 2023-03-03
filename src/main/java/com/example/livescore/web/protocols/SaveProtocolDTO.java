package com.example.livescore.web.protocols;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProtocolDTO {

    private Long gameId;
    private Long team1Id;
    private Long team2Id;
    private LocalDateTime dateAndTime;
}
