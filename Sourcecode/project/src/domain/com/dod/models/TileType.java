package com.dod.models;

/**
 * The type of a tile, i.e is this tile a wall, floor or something else.
 */
public enum TileType {
    Wall(0),
    Empty(1),
    Coin(2),
    Exit(3);

    private final int value;
    TileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
