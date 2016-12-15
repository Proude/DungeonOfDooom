package com.dod.bot.communicators;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Communicates move requests to the server
 */
public class MoveCommunicator extends CommunicatorBase {
    /**
     * Sends a request to the web service to move in a particular direction
     * @param direction String the direction to move in, a char from the set {W,A,S,D} corresponding to WASD directions.
     */
    public void moveDirection(String direction) {
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        params.add("key", direction);

        post("game/move", params);
    }
}
