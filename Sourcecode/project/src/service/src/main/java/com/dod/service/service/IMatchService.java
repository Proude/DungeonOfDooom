package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.MatchResultModel;
import com.dod.service.model.MatchStatus;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Manages joining/starting/ending matches.
 */
public interface IMatchService {

    /**
     * Creates a new Match
     * @param userName String username of the Player who is starting the Match
     * @param level int the number of the level to load for this Match
     * @return MatchStatus of the newly created Match
     */
    MatchStatus createMatch(String userName, int level);

    /**
     * Changes a Match's state to InGame
     * @param player Player whose ongoing Match will be modified
     */
    void startMatch(Player player);

    /**
     * Returns the MatchStatus for a particular Player's Match
     * @param player Player whose ongoing Match will be fetched
     * @return
     */
    MatchStatus getStatus(Player player);

    /**
     * Removes a Player from their current ongoing Match
     * @param player Player the Player whom will be removed from their ongoing Match
     */
    void leaveMatch(Player player);

    /**
     * Changes a Match's state to Over
     * @param player Player whose ongoing Match will be modified
     */
    void endMatch(Player player);

    /**
     * Adds the Player to a particular Match
     * @param player Player whom will be added
     * @param matchID UUID of the Match that player will be addd to
     * @throws SQLException thrown if Player doesn't exist or a SQL connectivity issue occurs
     */
    void joinMatch(Player player, UUID matchID) throws SQLException;

    /**
     * Get all Matches currently in the Lobbying state
     * @return MatchStatus[] array of all Matches in the Lobbying state
     */
    MatchStatus[] getLobbyingMatches();

    /**
     * Gets the MatchResultModel for a finished Match
     * todo why not remove the Player from the Match at this point rather than send another request?
     * @param player Player the Player that has a finished Match
     * @return MatchResultModel pertaining to the player's Match
     */
    MatchResultModel getMatchResult(Player player);
}
