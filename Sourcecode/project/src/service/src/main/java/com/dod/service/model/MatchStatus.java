package com.dod.service.model;

import com.dod.models.Match;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * Models the current state of a lobbying match.
 */
@XmlRootElement
public class MatchStatus
{
    private String[] playerNames;
    @XmlID
    private UUID id;
    private String state;

    public MatchStatus() {}

    public MatchStatus(Match match) {
        this.playerNames = match.getPlayerNames();
        this.id = match.getId();
        this.state = match.getState().toString();
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
