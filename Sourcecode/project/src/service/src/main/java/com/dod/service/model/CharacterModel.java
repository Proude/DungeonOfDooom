package com.dod.service.model;

import com.dod.models.Point;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A simpler model of Character for JSON parsing
 */
@XmlRootElement
public class CharacterModel {
    private String playerName;
    private int noCoins;
    private Point position;

    public CharacterModel() { }

    public CharacterModel(String playerName, int noCoins, Point position) {
        this.playerName = playerName;
        this.noCoins = noCoins;
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNoCoins() {
        return noCoins;
    }

    public void setNoCoins(int noCoins) {
        this.noCoins = noCoins;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
