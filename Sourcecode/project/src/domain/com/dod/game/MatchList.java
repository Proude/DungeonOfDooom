package com.dod.game;

import com.dod.models.Match;
import com.dod.models.MatchState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores the current ongoing matches in memory
 */
public class MatchList {

    private static List<Match> ongoingMatches = new ArrayList();

    public static void addMatch(Match match) {
        ongoingMatches.add(match);
    }

    public static List<Match> getLobbyingMatches() {
        List<Match> result = new ArrayList();

        for(Match match : ongoingMatches) {
            if(match.getState() == MatchState.Lobbying) {
                result.add(match);
            }
        }

        return result;
    }

    public static Match getMatch(UUID id) {
        Match result = null;

        for(Match match : ongoingMatches) {
            if(match.getId().equals(id)) {
                result = match;
                break;
            }
        }

        return result;
    }

    public static Match getMatchForPlayer(String username) {
        Match result = null;

        for(Match match: ongoingMatches) {
            if(match.hasCharacter(username)) {
                result = match;
                break;
            }
        }

        return result;
    }

    public static boolean playerHasMatch(String username) {
        return getMatchForPlayer(username) != null;
    }

    public static void removeMatch(UUID id) {
        for(Match match: ongoingMatches) {
            if(match.getId().equals(id)) {
                ongoingMatches.remove(match);
                break;
            }
        }
    }
}
