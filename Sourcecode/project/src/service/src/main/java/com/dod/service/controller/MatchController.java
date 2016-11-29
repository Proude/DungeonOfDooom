package com.dod.service.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages starting/joining matches
 */
@Path("match")
public class MatchController {

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
    @Path("new")
    public Response newMatch() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("start")
    public Response start() {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }
}
