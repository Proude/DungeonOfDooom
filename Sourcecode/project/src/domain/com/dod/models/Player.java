package com.dod.models;

/**
 * This is the model of the User.
 */
public class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }
}
