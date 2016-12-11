package com.dod.bot.communicators;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

/**
 * Handles match requests to the server
 */
public class MatchCommunicator extends CommunicatorBase {
    public void joinMatch(UUID matchId) {
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        params.add("matchId", matchId.toString());

        post("match/join", params);
    }
}
