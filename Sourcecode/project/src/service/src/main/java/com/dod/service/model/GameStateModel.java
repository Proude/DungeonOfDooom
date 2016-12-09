package com.dod.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the current GameState
 */
@XmlRootElement
public class GameStateModel {
    TileModel[] tiles;
    CharacterModel[] characters;

    public GameStateModel(TileModel[] tiles, CharacterModel[] characters) {
        this.tiles = tiles;
        this.characters = characters;
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
}
