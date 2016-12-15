package com.dod.bot.communicators;

import com.dod.service.model.GameStateModel;

/**
 * Communicates status requests to the server
 */
public class stateCommunicator extends CommunicatorBase {

    /**
     * Gets the current state from the web service.
     * @return GameStateModel a model representing the game's current state.
     */
    public GameStateModel getState() {
        return get("game/status").readEntity(GameStateModel.class);
    }

}
