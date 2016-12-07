package com.dod.models;

/**
 * The type of a tile, i.e is this tile a wall or something else.
 */
public enum TileType {
    Wall(0),
    Empty(1),
    Coin(2);

    private final int value;
    private TileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
