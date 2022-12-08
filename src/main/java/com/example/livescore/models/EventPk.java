package com.example.livescore.models;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class EventPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "protocol_id")
    private ProtocolEntity protocol;

    public Long getProtocolId() {
        return protocol.getProtocolId();
    }
}
