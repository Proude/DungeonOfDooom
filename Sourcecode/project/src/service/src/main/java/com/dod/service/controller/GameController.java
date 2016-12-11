package com.dod.service.controller;

import com.dod.game.MatchList;
import com.dod.models.Player;
import com.dod.service.model.GameStateModel;
import com.dod.service.service.StateService;
import com.dod.service.service.VisibilityService;
import org.glassfish.grizzly.http.server.Request;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages game functionality
 */
@Path("game")
public class GameController {

    @Context
    private Request request;
    StateService stateService;

    public GameController() {
        stateService = new StateService(new VisibilityService(), MatchList.instance());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public Response status() {
        String username = (String)request.getSession().getAttribute("player");
        GameStateModel state = stateService.GetState(new Player(username));

        return Response
                .ok()
                .entity(state)
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
}
