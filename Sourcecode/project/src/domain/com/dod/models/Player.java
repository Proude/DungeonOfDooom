package com.dod.models;

/**
 * This is the model of the User.
 */
public class Player {

    private String username;
    private String hashedPassword;
    private int level;
    private byte[] salt;
    private Score[] scoreData;

    public Player(String name) {
        this.username = name;
    }

    public Player(String name, String hashedPassword, byte[] salt) {
        this.username = name;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
