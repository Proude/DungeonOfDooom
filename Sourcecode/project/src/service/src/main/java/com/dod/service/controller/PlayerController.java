package com.dod.service.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages registering and logging in a player
 */
@Path("player")
public class PlayerController {


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response login() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("register")
    public Response register() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }
}
