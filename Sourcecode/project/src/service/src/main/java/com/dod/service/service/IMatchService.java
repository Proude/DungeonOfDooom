package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.MatchResultModel;
import com.dod.service.model.MatchStatus;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Interface for the MatchService
 * Handles game logic regarding match. Manages joining/starting/ending matches.
 */
public interface IMatchService {

    MatchStatus createMatch(String userName, int level);

    void startMatch(Player player);

    MatchStatus getStatus(Player player);

    void leaveMatch(Player player);
    void endMatch(Player player);
    void joinMatch(Player player, UUID matchID) throws SQLException;

    MatchStatus[] getLobbyingMatches();

    MatchResultModel getMatchResult(Player player);
}
