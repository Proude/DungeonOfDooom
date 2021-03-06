package com.dod.bot;

import java.util.UUID;

/**
 * Gets command parameters and intitialises bot
 */
public class Main {

    /**
     * Parses the input and starts the Bot
     * @param args expects 1 argument of ID for match to join
     */
    public static void main(String args[]) {
        UUID matchId = null;
        try {
            matchId = UUID.fromString(args[0]);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }

        Bot bot = new Bot();
        bot.play(matchId);
    }
}