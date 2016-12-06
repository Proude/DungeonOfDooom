package com.dod.service.service;

import com.dod.service.model.LoginModel;

import java.sql.SQLException;

/**
 * Interface for AuthenticationService
 * Handles authentiticating a user against their hashed password/username
 *
 */
public interface IAuthenticationService {

    boolean Register(LoginModel model) throws SQLException;
    boolean Login(LoginModel model);

}
