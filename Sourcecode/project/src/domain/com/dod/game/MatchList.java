package com.dod.game;

import com.dod.models.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores the current ongoing matches in memory
 */
public class MatchList {

    private static List<Match> ongoingMatches = new ArrayList();

    private static void addMatch(Match match) {
        ongoingMatches.add(match);
    }

    private static Match getMatchForPlayer(String username) {
        Match result = null;

        for(Match match: ongoingMatches) {
            if(match.hasCharacter(username)) {
                result = match;
                break;
            }
        }

        return result;
    }

    private static boolean playerHasMatch(String username) {
        return getMatchForPlayer(username) != null;
    }

    private static void removeMatch(UUID id) {
        for(Match match: ongoingMatches) {
            if(match.getId().equals(id)) {
                ongoingMatches.remove(match);
                break;
            }
        }
    }
}
