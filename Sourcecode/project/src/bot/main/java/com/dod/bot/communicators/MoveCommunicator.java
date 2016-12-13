package com.dod.bot.communicators;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Communicates move requests to the server
 */
public class MoveCommunicator extends CommunicatorBase {
    public void moveDirection(String direction) {
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        params.add("key", direction);

        post("game/move", params);
    }
}
