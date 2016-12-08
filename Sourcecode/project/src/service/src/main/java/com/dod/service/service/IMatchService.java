package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.MatchStatus;

import java.util.UUID;

/**
 * Interface for the MatchService
 * Handles game logic regarding match. Manages joining/starting/ending matches.
 */
public interface IMatchService {

    MatchStatus createMatch(String userName, int level);

    void startMatch(UUID id);

    MatchStatus getStatus(Player player);

    void leaveMatch(Player player);
    void endMatch(Player player);
    void joinMatch(Player player, UUID matchID);

    MatchStatus[] getLobbyingMatches();
}
