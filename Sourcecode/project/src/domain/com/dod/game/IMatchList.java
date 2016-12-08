package com.dod.game;

import com.dod.models.Match;

import java.util.List;
import java.util.UUID;

/**
 * Stores ongoing matches in memory
 */
public interface IMatchList {
    void addMatch(Match match);

    List<Match> getLobbyingMatches();

    Match getMatch(UUID id);

    Match getMatchForPlayer(String username);

    boolean playerHasMatch(String username);

    void removeMatch(UUID id);
}
