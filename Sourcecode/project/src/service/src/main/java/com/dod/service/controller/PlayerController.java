package com.dod.service.controller;

import com.dod.db.repositories.PlayerRepository;
import com.dod.models.Player;
import com.dod.service.model.LoginModel;
import com.dod.service.service.AuthenticationService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Manages registering and logging in a player
 */
@Path("player")
public class PlayerController {

    AuthenticationService service;

    public PlayerController() {
        service = new AuthenticationService(new PlayerRepository());
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        service.Login(new LoginModel(username, password));

        return Response
                .ok()
                .entity(username + " " + password)
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("register")
    public Response register(@FormParam("username") String username, @FormParam("password") String password) {
        return Response
                .ok()
                .entity(username + " " + password)
                .build();
    }
}
