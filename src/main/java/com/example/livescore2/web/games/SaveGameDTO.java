package com.example.livescore2.web.games;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveGameDTO {
    private Long groupId;
    private Long protocolId;
    private boolean isPlayed;
}
