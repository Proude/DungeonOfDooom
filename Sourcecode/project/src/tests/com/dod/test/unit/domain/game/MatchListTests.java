package dod.test.unit.domain.game;

import com.dod.game.MatchList;
import com.dod.models.Match;
import com.dod.models.MatchState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Tests for MatchList
 */
public class MatchListTests {

    private final String testUsername = "testUsername";

    @Test
    public void shouldGetLobbyingMatches() {
        Match lobbyingMatch = mock(Match.class);
        Match ingameMatch = mock(Match.class);
        Match anotherIngameMatch = mock(Match.class);

        when(lobbyingMatch.getState()).thenReturn(MatchState.Lobbying);
        when(ingameMatch.getState()).thenReturn(MatchState.Ingame);
        when(anotherIngameMatch.getState()).thenReturn(MatchState.Ingame);

        MatchList.addMatch(lobbyingMatch);
        MatchList.addMatch(ingameMatch);
        MatchList.addMatch(anotherIngameMatch);

        List<Match> result = MatchList.getLobbyingMatches();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(lobbyingMatch, result.get(0));

        //Cleanup (because static)
        UUID id = UUID.randomUUID();
        when(lobbyingMatch.getId()).thenReturn(id);
        when(ingameMatch.getId()).thenReturn(id);
        when(anotherIngameMatch.getId()).thenReturn(id);

        MatchList.removeMatch(id);
        MatchList.removeMatch(id);
        MatchList.removeMatch(id);
    }

    @Test
    public void shouldGetMatchById() {
        Match matchOne = mock(Match.class);
        Match matchTwo = mock(Match.class);
        UUID idOne = UUID.randomUUID();
        UUID idTwo = UUID.randomUUID();

        when(matchOne.getId()).thenReturn(idOne);
        when(matchTwo.getId()).thenReturn(idTwo);

        MatchList.addMatch(matchOne);
        MatchList.addMatch(matchTwo);

        Match result = MatchList.getMatch(idOne);

        Assert.assertEquals(matchOne, result);

        //Cleanup (because static)
        MatchList.removeMatch(idOne);
        MatchList.removeMatch(idTwo);
    }

    @Test
    public void shouldGetMatchForPlayer() {
        Match match = mock(Match.class);
        when(match.hasCharacter(testUsername)).thenReturn(true);

        MatchList.addMatch(match);

        Match result = MatchList.getMatchForPlayer(testUsername);

        Assert.assertEquals(match,result);

        //Cleanup
        UUID id = UUID.randomUUID();
        when(match.getId()).thenReturn(id);
        MatchList.removeMatch(id);
    }
}
