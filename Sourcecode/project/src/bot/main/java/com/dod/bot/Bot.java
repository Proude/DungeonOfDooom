package com.dod.bot;

import com.dod.service.model.GameStateModel;
import com.dod.bot.communicators.MatchCommunicator;
import com.dod.bot.communicators.MoveCommunicator;
import com.dod.bot.communicators.stateCommunicator;
import com.dod.service.model.TileModel;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * The bot
 */
public class Bot {
    private MatchCommunicator matchCommunicator;
    private MoveCommunicator moveCommunicator;
    private com.dod.bot.communicators.stateCommunicator stateCommunicator;

    private double delta;
    private double timestep = 200 * 1000000;
    private long previousTime;

    private boolean isPlaying = false;
    private GameStateModel state;

    private Random random;

    public Bot() {
        this.matchCommunicator = new MatchCommunicator();
        this.moveCommunicator = new MoveCommunicator();
        this.stateCommunicator = new stateCommunicator();
        random = new Random();
    }

    public void play(UUID matchId) {
        isPlaying = true;
        matchCommunicator.joinMatch(matchId);
        state = stateCommunicator.getState();
        delta = 0;
        previousTime = System.nanoTime();

        while(isPlaying) {
            long currentTime = System.nanoTime();
            delta += currentTime - previousTime;
            previousTime = currentTime;

            if(delta > timestep) {
                delta -= timestep;

                state = stateCommunicator.getState();
                if (random.nextBoolean()) {
                    moveCommunicator.moveDirection(random.nextBoolean() ? "A" : "D");
                } else {
                    moveCommunicator.moveDirection(random.nextBoolean() ? "W" : "S");
                }

                if (state.isHasEnded()) {
                    isPlaying = false;
                }
            }
        }
    }
}