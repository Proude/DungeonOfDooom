package com.dod.service.controller;

import com.dod.game.MatchList;
import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.service.model.GameStateModel;
import com.dod.service.service.MovementService;
import com.dod.service.service.StateService;
import com.dod.service.service.VisibilityService;
import org.glassfish.grizzly.http.server.Request;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("move")
    public Response move(@NotNull @FormParam("key") String direction) {
        MovementService moveService = new MovementService();
        String username = (String)request.getSession().getAttribute("player");
        moveService.Move(direction, new Player(username));
        GameStateModel state = stateService.GetState(new Player(username));

        return Response
                .ok()
                .entity(state)
                .build();
    }
}
