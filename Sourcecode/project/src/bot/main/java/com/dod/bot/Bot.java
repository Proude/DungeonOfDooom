package com.dod.bot;

import com.dod.service.model.GameStateModel;
import com.dod.bot.communicators.MatchCommunicator;
import com.dod.bot.communicators.MoveCommunicator;
import com.dod.bot.communicators.stateCommunicator;
import com.dod.service.model.TileModel;

import java.util.List;
import java.util.UUID;

/**
 * The bot
 */
public class Bot {
    private MatchCommunicator matchCommunicator;
    private MoveCommunicator moveCommunicator;
    private com.dod.bot.communicators.stateCommunicator stateCommunicator;

    private boolean isPlaying = false;
    private GameStateModel state;

    public Bot() {
        this.matchCommunicator = new MatchCommunicator();
        this.moveCommunicator = new MoveCommunicator();
        this.stateCommunicator = new stateCommunicator();
    }

    public void play(UUID matchId) {
        isPlaying = true;
        matchCommunicator.joinMatch(matchId);
        state = stateCommunicator.getState();

        while(isPlaying) {

            isPlaying = false;
        }
    }
}