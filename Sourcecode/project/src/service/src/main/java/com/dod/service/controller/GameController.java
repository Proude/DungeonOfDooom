package com.dod.service.controller;

import com.dod.service.service.StateService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages game functionality
 */
@Path("game")
public class GameController {

    StateService stateService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("status")
    public Response status() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("move")
    public Response move() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("leave")
    public Response leave() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }
}
