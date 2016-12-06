package com.dod.service.model;

import com.dod.models.Player;

/**
 * Used for logging in and registering.
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

    public Player asPlayer() {
        return new Player(userName);
    }
}
