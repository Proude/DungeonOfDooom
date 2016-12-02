package com.dod.models;

/**
 * This is the model of the User.
 */
public class Player {

    private String username;
    private String password;
    private int level;
    private Score[] scoreData;

    public Player(String name) {
        this.username = name;
    }

    public Player(String name, String password) { this.username = name; this.password = password; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
