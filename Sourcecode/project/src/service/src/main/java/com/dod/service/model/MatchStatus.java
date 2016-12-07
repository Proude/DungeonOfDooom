package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * The current state of a lobbying match, for communication with client
 */
@XmlRootElement
public class MatchStatus
{
    String[] playerNames;
    UUID id;

    public MatchStatus(String[] playerNames, UUID id) {
        this.playerNames = playerNames;
        this.id = id;
    }
}
