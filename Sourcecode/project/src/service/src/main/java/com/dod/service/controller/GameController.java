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
import java.sql.SQLException;

/**
 * A controller to manage in-game game-related functionality ie getting the current state of the world or moving.
 */
@Path("game")
public class GameController {

    @Context
    private Request request;
    StateService stateService;
    MovementService movementService;

    public GameController() {
        stateService = new StateService(new VisibilityService(), MatchList.instance());
        movementService = new MovementService();
    }

    /**
     * Responds with the current gamestate from the Player's Character's perspective, i.e. only returning visible tiles
     * If Player has no current ongoing Match returns 500 error.
     * @return Response 200 OK with GameStateModel as a JSON object
     */
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

    /**
     * An endpoint to request the Player's Character move once in a particular direction.
     * Responds with game status after move.
     * If Player has no current ongoing Match returns 500 error.
     * @param direction a char from {W,S,A,D} pertaining to a particular direction in the WASD layout, must not be null
     * @return Response 200 OK with GameStateModel as a JSON object
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("move")
    public Response move(@NotNull @FormParam("key") String direction) {
        String username = (String)request.getSession().getAttribute("player");
        try {
            movementService.Move(direction, new Player(username));
        }
        catch(SQLException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        GameStateModel state = stateService.GetState(new Player(username));

        return Response
                .ok()
                .entity(state)
                .build();
    }
}
