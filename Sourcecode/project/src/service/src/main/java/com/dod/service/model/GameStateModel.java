package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import com.dod.models.Character;

/**
 * Represents the current GameState
 */
@XmlRootElement
public class GameStateModel {
    private TileModel[] tiles;
    private CharacterModel[] characters;
    private CharacterModel playerCharacter;
    private boolean hasEnded;

    public GameStateModel() { }

    public GameStateModel(TileModel[] tiles, CharacterModel[] characters, CharacterModel playerCharacter, boolean hasEnded) {
        this.tiles = tiles;
        this.characters = characters;
        this.playerCharacter = playerCharacter;
        this.hasEnded = hasEnded;
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

    public CharacterModel getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(CharacterModel playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public boolean isHasEnded() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }
}
