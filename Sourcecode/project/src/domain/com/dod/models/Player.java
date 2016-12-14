package com.dod.models;

/**
 * <pre>
 *     A Player represents the user that is in control of the game client
 *     A Player can sign in with a username or password
 *     A Player has a level and a password salt
 *     A Player's password is always hashed
 * </pre>
 */
public class Player {

    private String username;
    private String hashedPassword;
    private int level;
    private byte[] salt;

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
