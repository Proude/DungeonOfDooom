package com.dod.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is a model of Score that is used by the player.
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
