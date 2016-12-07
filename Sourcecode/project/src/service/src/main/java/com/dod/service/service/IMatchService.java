package com.dod.service.service;

import com.dod.service.model.MatchStatus;

/**
 * Interface for the MatchService
 * Handles game logic regarding match. Manages joining/starting/ending matches.
 */
public interface IMatchService {

    MatchStatus createMatch(String userName, int level);
    void initMatch();
    void getStatus(); //todo return Match model
    void leaveMatch();
    void endMatch();
    void joinMatch();
    MatchStatus[] getLobbyingMatches();
}
