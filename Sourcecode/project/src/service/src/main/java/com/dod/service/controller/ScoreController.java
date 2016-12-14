package com.dod.service.controller;

import com.dod.db.repositories.IScoreRepository;
import com.dod.db.repositories.ScoreRepository;
import com.dod.service.model.ScoreboardModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Fetches and returns the top scores
 */
@Path("score")
public class ScoreController {

    private IScoreRepository repository;

    public ScoreController() {
        this.repository = new ScoreRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("top")
    public Response top() {
        ScoreboardModel scoreBoard = null;

        try {
            scoreBoard = new ScoreboardModel(repository.getHighestScores());
        }
        catch(SQLException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        return
                Response.ok()
                .entity(scoreBoard)
                .build();
    }
}
