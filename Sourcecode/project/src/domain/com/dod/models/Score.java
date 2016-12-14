package com.dod.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 *     A Score stores the points a Player achieved when they completed a Match.
 *     A Score as an ID in order to store the Score as a unique databaes record
 *     A Score also has a value and the username of the player that the score is related to.
 * </pre>
 */
@XmlRootElement
public class Score {
    private int id;
    private String username;
    private int value;

    public Score(int id, String username, int value) {
        this.id = id;
        this.username = username;
        this.value = value;
    }

    public Score(String username, int value) {
        this.id = -1;
        this.username = username;
        this.value = value;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
