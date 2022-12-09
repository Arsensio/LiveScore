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
public class ProtocolDTO {
    private Long protocolId;
    private String team1;
    private String team2;
    private LocalDateTime dateAndTime;
    private String gameScore;
}
