package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.MatchStatus;

/**
 * Interface for the MatchService
 * Handles game logic regarding match. Manages joining/starting/ending matches.
 */
public interface IMatchService {

    MatchStatus createMatch(String userName, int level);

    void startMatch();

    MatchStatus getStatus(String username);

    void leaveMatch();
    void endMatch();
    void joinMatch();

    void joinMatch(Player player);

    MatchStatus[] getLobbyingMatches();
}
