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

    private Long protocolId;
    private Long gameId;
    private Long team1;
    private Long team2;
    private LocalDateTime dateAndTime;
    private boolean isFinished;

}
