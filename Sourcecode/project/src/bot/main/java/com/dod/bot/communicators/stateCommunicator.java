package com.dod.bot.communicators;

import com.dod.service.model.GameStateModel;

/**
 * Communicates status requests to the server
 */
public class stateCommunicator extends CommunicatorBase {
    public GameStateModel getState() {
        return get("game/status").readEntity(GameStateModel.class);
    }
}
