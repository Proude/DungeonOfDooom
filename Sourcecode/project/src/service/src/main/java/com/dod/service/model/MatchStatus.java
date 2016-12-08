package com.dod.service.model;

import com.dod.models.Match;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * The current state of a lobbying match, for communication with client
 */
@XmlRootElement
public class MatchStatus
{
    private String[] playerNames;
    @XmlID
    private UUID id;

    public MatchStatus() {}

    public MatchStatus(Match match) {
        this.playerNames = match.getPlayerNames();
        this.id = match.getId();
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public UUID getId() {
        return id;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
