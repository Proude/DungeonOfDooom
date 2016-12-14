package com.dod.service.controller;

import com.dod.db.repositories.PlayerRepository;
import com.dod.service.model.LoginModel;
import com.dod.service.service.AuthenticationService;
import com.dod.service.service.IAuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import org.glassfish.grizzly.http.server.Request;
import org.hibernate.validator.constraints.Length;

/**
 * <pre>
 *      Manages registering and logging in a player
 *      Creates the session that other controllers can use to fetch user details
 * </pre>
 */
@Path("player")
public class PlayerController {

    @Context
    private Request request;
    IAuthenticationService service;

    public PlayerController() {
        service = new AuthenticationService(new PlayerRepository());
    }

    /**
     * Authorises a user and starts a session with them
     * @param username must be unique, not empty and less than 256 characters
     * @param password must not be empty and less than 256 characters
     * @return Response with blank body, 200 if successful otherwise 400 or 500
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response login(
            @NotNull @Length(min = 1, max =255) @FormParam("username") String username,
            @NotNull @Length(min = 1, max =255) @FormParam("password") String password
    ) {
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
     * @return Response with blank body, 200 if successful otherwise 400 or 500
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("register")
    public Response register(
            @NotNull @Length(min = 1, max =255) @FormParam("username") String username,
            @NotNull @Length(min = 1, max = 255) @FormParam("password") String password
    ) {
        boolean success = service.Register(new LoginModel(username,password));
        if(success) {
            request.getSession(true);
            request.getSession().setAttribute("player", username);
            return Response.ok().build();
        }
        else {
            return Response.status(400).build();
        }
    }
}
