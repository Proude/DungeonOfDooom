package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the current GameState. Intended to be communicated to the client via JSON encoding.
 */
@XmlRootElement
public class GameStateModel {
    private TileModel[] tiles;
    private CharacterModel[] characters;
    private CharacterModel playerCharacter;
    private int minNumOfCoins;
    private boolean hasEnded;

    public GameStateModel() { }

    public GameStateModel(TileModel[] tiles, CharacterModel[] characters, CharacterModel playerCharacter, boolean hasEnded, int minNumOfCoins) {
        this.tiles = tiles;
        this.characters = characters;
        this.playerCharacter = playerCharacter;
        this.hasEnded = hasEnded;
        this.minNumOfCoins = minNumOfCoins;
    }

    public TileModel[] getTiles() {
        return tiles;
    }

    public void setTiles(TileModel[] tiles) {
        this.tiles = tiles;
    }

    public CharacterModel[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterModel[] characters) {
        this.characters = characters;
    }

    /**
     * The Character belonging to the Player that made the request
     * @return Character
     */
    public CharacterModel getPlayerCharacter() {
        return playerCharacter;
    }

    /**
     * The Character belonging to the Player that made the request
     * @param playerCharacter Character
     */
    public void setPlayerCharacter(CharacterModel playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    /**
     * Whether the match is ongoing- triggers the client's endgame if true
     * @return boolean
     */
    public boolean isHasEnded() {
        return hasEnded;
    }

    /**
     * Whether the match is ongoing- triggers the client's endgame if true
     * @param hasEnded boolean
     */
    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    /**
     * The minimum number of coins needed to win the Match
     * @return int
     */
    public int getMinNumOfCoins() { return minNumOfCoins; }

    /**
     * * The minimum number of coins needed to win the Match
     * @param minNumOfCoins int
     */
    public void setMinNumOfCoins(int minNumOfCoins) { this.minNumOfCoins = minNumOfCoins; }
}
