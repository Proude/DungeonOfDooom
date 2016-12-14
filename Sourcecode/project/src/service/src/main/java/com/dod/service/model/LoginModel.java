package com.dod.service.model;

import com.dod.models.Player;

/**
 * Simple model/bean used to pass information to/from the AuthorisationService
 */
public class LoginModel {

    private String userName;
    private String password;

    public LoginModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Convenience method to return the LoginModel's username in the Player model
     * @return Player
     */
    public Player asPlayer() {
        return new Player(userName);
    }
}
