package com.dod.game;

import com.dod.models.Match;
import com.dod.models.MatchState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * Implementation of IMatchList
 * Stores ongoing matches in memory and provides functions to access these matches.
 * Uses a singleton so that we can fetch the same object between requests
 *     (And because this is much easier to test than making all methods static)
 * </pre>
 */
public class MatchList implements IMatchList {

    private static IMatchList instance;

    /**
     * Returns a singleton instance of MatchList, creating it if it hasn't been initialised yet.
     * @return MatchList
     */
    public static IMatchList instance() {
        if(instance == null) {
            instance = new MatchList();
        }
        return instance;
    }

    private List<Match> ongoingMatches = new ArrayList();

    /**
     * Adds a match to the list
     * @param match the match to add
     */
    public void addMatch(Match match) {
        ongoingMatches.add(match);
    }

    /**
     * Gets all matches that are in the Lobbying state
     * @return List of Match objects
     */
    public List<Match> getLobbyingMatches() {
        List<Match> result = new ArrayList();

        for(Match match : ongoingMatches) {
            if(match.getState() == MatchState.Lobbying) {
                result.add(match);
            }
        }

        return result;
    }

    /**
     * Gets a Match by a particular ID. Returns null if the match is missing.
     * @param id the UUID that corresponds to the match to be fetched
     * @return Match
     */
    public Match getMatch(UUID id) {
        Match result = null;

        for(Match match : ongoingMatches) {
            if(match.getId().equals(id)) {
                result = match;
                break;
            }
        }

        return result;
    }

    /**
     * Gets a match by player name. Each player should only have one match. Returns null if player has no match.
     * @param username the username of the player
     * @return Match
     */
    public Match getMatchForPlayer(String username) {
        Match result = null;

        for(Match match: ongoingMatches) {
            if(match.hasCharacter(username)) {
                result = match;
                break;
            }
        }

        return result;
    }

    /**
     * Returns true if the player has a match in the list
     * @param username the player's username
     * @return true if the player has a match in the list otherwise false
     */
    public boolean playerHasMatch(String username) {
        return getMatchForPlayer(username) != null;
    }

    /**
     * Removes the match fitting the specified ID from the list
     * @param id the UUID that corresponds to the particular Match to be removed
     */
    public void removeMatch(UUID id) {
        for(Match match: ongoingMatches) {
            if(match.getId().equals(id)) {
                ongoingMatches.remove(match);
                break;
            }
        }
    }
}
