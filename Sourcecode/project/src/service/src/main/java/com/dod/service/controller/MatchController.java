package com.dod.service.controller;

import com.dod.service.model.MatchStatus;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Manages starting/joining matches
 */
@Path("match")
public class MatchController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public MatchStatus status() {
        return new MatchStatus(new String[] {"wat", "wot"}, UUID.randomUUID());
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

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("join")
    public Response join(
            @NotNull @FormParam("matchId") UUID matchId
    ) {
        return Response
                .ok()
                .entity("unimplemented")
                .build();
    }

    //todo: add bot endpoint?
}
