package com.dod.game;

import com.dod.models.Match;
import com.dod.models.MatchState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of IMatchList
 * Uses a singleton (instance()) so that we can fetch the same object between requests
 *  (And because this is much easier to test than making all methods static)
 */
public class MatchList implements IMatchList {

    private static IMatchList instance;

    public static IMatchList instance() {
        if(instance == null) {
            instance = new MatchList();
        }
        return instance;
    }

    private List<Match> ongoingMatches = new ArrayList();

    public void addMatch(Match match) {
        ongoingMatches.add(match);
    }

    public List<Match> getLobbyingMatches() {
        List<Match> result = new ArrayList();

        for(Match match : ongoingMatches) {
            if(match.getState() == MatchState.Lobbying) {
                result.add(match);
            }
        }

        return result;
    }

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

    public boolean playerHasMatch(String username) {
        return getMatchForPlayer(username) != null;
    }

    public void removeMatch(UUID id) {
        for(Match match: ongoingMatches) {
            if(match.getId().equals(id)) {
                ongoingMatches.remove(match);
                break;
            }
        }
    }
}
