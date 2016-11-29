package com.dod.service.service;

import com.dod.service.model.LoginModel;

/**
 * Interface for AuthenticationService
 * Handles authentiticating a user against their hashed password/username
 *
 */
public interface AuthenticationService {

    void Register(LoginModel model);
    void Login(LoginModel model);

}
