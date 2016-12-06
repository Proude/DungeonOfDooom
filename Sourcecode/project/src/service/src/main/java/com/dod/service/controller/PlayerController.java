package com.dod.service.controller;

import com.dod.db.repositories.PlayerRepository;
import com.dod.service.model.LoginModel;
import com.dod.service.service.AuthenticationService;
import com.dod.service.service.IAuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Manages registering and logging in a player
 * Creates the session that other controllers can use to fetch user details
 */
@Path("player")
public class PlayerController {

    @Context
    private HttpServletRequest request;
    IAuthenticationService service;

    public PlayerController() {
        service = new AuthenticationService(new PlayerRepository());
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        boolean isAuthorised = service.Login(new LoginModel(username, password));
        if(isAuthorised) {
            request.getSession(true);
            request.getSession().setAttribute("player",username);
            return Response.ok().build();
        }
        else {
            return Response
                    .status(403)
                    .build();
        }
    }

    /**
     * Registers a user for the service. Username must be unique.
     * @param username must be unique, not empty and less than 256 characters
     * @param password must not be empty and less than 256 characters
     * @return
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("register")
    public Response register(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            boolean success = service.Register(new LoginModel(username,password));
            if(success) {
                request.getSession(true);
                request.getSession().setAttribute("player", username);
                return Response.ok().build();
            }
            else {
                return Response.status(409).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }

    }
}
