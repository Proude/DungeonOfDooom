package com.dod.service.controller;

import com.dod.db.repositories.PlayerRepository;
import com.dod.game.MatchList;
import com.dod.models.Match;
import com.dod.service.model.MatchStatus;
import com.dod.service.service.IOService;
import com.dod.service.service.MatchService;
import com.dod.service.service.ParseService;
import org.glassfish.grizzly.http.server.Request;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Manages starting/joining matches
 */
@Path("match")
public class MatchController {

    @Context
    private Request request;

    private MatchService matchService;


    public MatchController() {
        this.matchService = new MatchService(
                new IOService(),
                new ParseService(),
                new PlayerRepository(),
                MatchList.instance());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public MatchStatus status() {
        String username = (String)request.getSession().getAttribute("player");
        return matchService.getStatus(username);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("new")
    public Response newMatch(
            @NotNull @FormParam("level") int level
    ) {
        String userName = (String)request.getSession().getAttribute("player");

        MatchStatus newMatch = matchService.createMatch(userName, level);

        if(newMatch != null) {
            return Response
                    .ok()
                    .entity(newMatch)
                    .build();
        }
        else {
            return Response.serverError().build();
        }
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response list() {
        MatchStatus[] matches = matchService.getLobbyingMatches();

        return Response
                .ok()
                .entity(matches)
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
