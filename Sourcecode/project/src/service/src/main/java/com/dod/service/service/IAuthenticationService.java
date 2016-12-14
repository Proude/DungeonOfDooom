package com.dod.service.service;

import com.dod.service.model.LoginModel;

import java.sql.SQLException;

/**
 * <pre>
 *     Handles authenticating a user against their user/pass combo
 * </pre>
 */
public interface IAuthenticationService {
    /**
     * Registers a new user
     * @param model LoginModel containing the user/pass to be registered
     * @return boolean true if successful otherwise false
     */
    boolean Register(LoginModel model);
    /**
     * Registers a new user
     * @param model LoginModel containing the user/pass to be authorised
     * @return boolean true if the user is authorised, otherwise false
     */
    boolean Login(LoginModel model);

}
