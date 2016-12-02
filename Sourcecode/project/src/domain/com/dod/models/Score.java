package com.dod.models;

/**
 * This class is a model of Score that is used by the player.
 */
public class Score {
    private int id;
    private int value;
    // might need relation with player

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
