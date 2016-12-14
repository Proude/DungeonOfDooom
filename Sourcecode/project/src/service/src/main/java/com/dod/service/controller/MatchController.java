package com.dod.service.controller;

import com.dod.db.repositories.PlayerRepository;
import com.dod.game.MatchList;
import com.dod.models.Match;
import com.dod.models.Player;
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
import java.sql.SQLException;
import java.util.UUID;

/**
 * A controller to manage Matches- joining, listing, starting a new one etc.
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

    /**
     * Responds with the status of the player's current Match.
     * If Player has no current Match returns a 500 error.
     * @return Response 200 OK with MatchStatus encoded in JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public Response status() {
        String username = (String)request.getSession().getAttribute("player");
        return Response
            .ok()
            .entity(matchService.getStatus(new Player(username)))
            .build();
    }

    /**
     * Starts a new Match in a particular level and responds with that Match's status
     * @param level int the level to load for this Match, must not be null
     * @return Response 200 OK with MatchStatus encoded in JSON or null if a Match cannot be crated
     */
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

    /**
     * Changes a Match's status to Ingame (marking the start of the Match for all players)
     * @return MatchStatus encoded in JSON
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("start")
    public Response start() {
        String username = (String)request.getSession().getAttribute("player");

        matchService.startMatch(new Player(username));

        return Response
                .ok()
                .build();
    }

    /**
     * Lists all currently lobbying matches in a JSON array
     * @return Response 200 OK JSON array with encoded MatchStatus for each lobbying Match
     */
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

    /**
     * Joins the Player in an ongoing Match
     * @param matchId the UUID ID of the Match, must not be null
     * @return Response 200 OK with the latest MatchStatus encoded in JSON
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("join")
    public Response join(
            @NotNull @FormParam("matchId") UUID matchId
    ) {
        String username = (String)request.getSession(false).getAttribute("player");
        try {
            matchService.joinMatch(new Player(username), matchId);
            return Response
                    .ok()
                    .entity(matchService.getStatus(new Player(username)))
                    .build();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return Response
                    .serverError()
                    .build();
        }
    }

    /**
     * Removes the Player from their current Match
     * @return Response 200 OK with a blank body
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("leave")
    public Response leave() {
        String username = (String)request.getSession(false).getAttribute("player");
        matchService.leaveMatch(new Player(username));

        return Response
                .ok()
                .build();
    }

    /**
     * Fetches the result of a Match from memory
     * @return Resepons 200 OK with JSON encoded MatchResultModel
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("result")
    public Response result() {
        String username = (String)request.getSession(false).getAttribute("player");

        return Response
                .ok()
                .entity(matchService.getMatchResult(new Player(username)))
                .build();
    }
}
