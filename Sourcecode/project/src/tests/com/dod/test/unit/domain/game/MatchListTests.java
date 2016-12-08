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

    private MatchList matchList;

    @Before
    public void Setup() {
        matchList = new MatchList();
    }

    @Test
    public void shouldGetLobbyingMatches() {
        Match lobbyingMatch = mock(Match.class);
        Match ingameMatch = mock(Match.class);
        Match anotherIngameMatch = mock(Match.class);

        when(lobbyingMatch.getState()).thenReturn(MatchState.Lobbying);
        when(ingameMatch.getState()).thenReturn(MatchState.Ingame);
        when(anotherIngameMatch.getState()).thenReturn(MatchState.Ingame);

        matchList.addMatch(lobbyingMatch);
        matchList.addMatch(ingameMatch);
        matchList.addMatch(anotherIngameMatch);

        List<Match> result = matchList.getLobbyingMatches();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(lobbyingMatch, result.get(0));
    }

    @Test
    public void shouldGetMatchById() {
        Match matchOne = mock(Match.class);
        Match matchTwo = mock(Match.class);
        UUID idOne = UUID.randomUUID();
        UUID idTwo = UUID.randomUUID();

        when(matchOne.getId()).thenReturn(idOne);
        when(matchTwo.getId()).thenReturn(idTwo);

        matchList.addMatch(matchOne);
        matchList.addMatch(matchTwo);

        Match result = matchList.getMatch(idOne);

        Assert.assertEquals(matchOne, result);
    }

    @Test
    public void shouldGetMatchForPlayer() {
        Match match = mock(Match.class);
        when(match.hasCharacter(testUsername)).thenReturn(true);

        matchList.addMatch(match);

        Match result = matchList.getMatchForPlayer(testUsername);

        Assert.assertEquals(match,result);
    }
}
