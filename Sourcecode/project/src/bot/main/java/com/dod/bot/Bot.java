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
 * <pre>
 * The main bot object. Makes basic decisions and uses the Communicators to enact these decisions.
 * Has no real intelligence at the moment. In the future we could make it much more intelligent using the Map class
 * to store beliefs about the world and use path-finding to hunt out gold to get the most score.
 * </pre>
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

    /**
     * Joins a match and then randomly picks a direction to move in every 5th of a second.
     * Stops when the Match is over.
     * @param matchId UUID The ID of the match to join
     */
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