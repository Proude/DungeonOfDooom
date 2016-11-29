package com.dod.service.service;

/**
 * Interface for the MatchService
 * Handles game logic regarding match. Manages joining/starting/ending matches.
 */
public interface IMatchService {

    int createMatch();
    void initMatch();
    void getStatus(); //todo return Match model
    void LeaveMatch();
    void EndMatch();
    void JoinMatch();

}
