package com.dod.game;

import com.dod.models.Match;

import java.util.List;
import java.util.UUID;

/**
 * Stores ongoing matches in memory and provides functions to access these matches.
 */
public interface IMatchList {

    /**
     * Returns a singleton instance of MatchList, creating it if it hasn't been initialised yet.
     * @return MatchList
     */
    void addMatch(Match match);

    /**
     * Gets all matches that are in the Lobbying state
     * @return List of Match objects
     */
    List<Match> getLobbyingMatches();

    /**
     * Gets a Match by a particular ID. Returns null if the match is missing.
     * @param id the UUID that corresponds to the match to be fetched
     * @return Match
     */
    Match getMatch(UUID id);

    /**
     * Gets a match by player name. Each player should only have one match. Returns null if player has no match.
     * @param username the username of the player
     * @return Match
     */
    Match getMatchForPlayer(String username);

    /**
     * Returns true if the player has a match in the list
     * @param username the player's username
     * @return true if the player has a match in the list otherwise false
     */
    boolean playerHasMatch(String username);

    /**
     * Removes the match fitting the specified ID from the list
     * @param id the UUID that corresponds to the particular Match to be removed
     */
    void removeMatch(UUID id);
}
