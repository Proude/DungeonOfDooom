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
    private Character playerCharacter;

    public GameStateModel() { }

    public GameStateModel(TileModel[] tiles, CharacterModel[] characters, Character playerCharacter) {
        this.tiles = tiles;
        this.characters = characters;
        this.playerCharacter = playerCharacter;
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

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(Character playerCharacter) {
        this.playerCharacter = playerCharacter;
    }
}
