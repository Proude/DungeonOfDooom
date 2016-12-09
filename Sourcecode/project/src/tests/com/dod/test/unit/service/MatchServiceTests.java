package dod.test.unit.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.game.IMatchList;
import com.dod.game.MatchList;
import com.dod.models.*;
import com.dod.service.model.MatchStatus;
import com.dod.service.service.IIOService;
import com.dod.service.service.IOService;
import com.dod.service.service.IParseService;
import com.dod.service.service.MatchService;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Tests for MatchService
 */
public class MatchServiceTests {

    MatchService service;

    IIOService ioServiceMock;
    IParseService parseServiceMock;
    IPlayerRepository playerRepositoryMock;
    IMatchList matchListSpy;
    Map mapMock;
    Player playerMock;

    private final int testLevelNo = 0;
    private final String testLevelPath = "/maps/level0.json";
    private final String testUsername = "testUsername";
    private final Point testPoint = new Point(0,0);
    private final int testNumberOfCoins = 10;

    @Before
    public void setup() {
        ioServiceMock = mock(IOService.class);
        parseServiceMock = mock(IParseService.class);
        playerRepositoryMock = mock(IPlayerRepository.class);
        matchListSpy = spy(new MatchList());
        mapMock = mock(Map.class);
        playerMock = mock(Player.class);
        when(playerMock.getUsername()).thenReturn(testUsername);

        service = new MatchService(ioServiceMock, parseServiceMock, playerRepositoryMock, matchListSpy);
    }

    @Test
    public void shouldCreateMatch() throws Exception {
        when(ioServiceMock.getJsonObject(any(String.class))).thenReturn(new JSONObject());
        when(parseServiceMock.parseMap(any(JSONObject.class))).thenReturn(mapMock);
        when(playerRepositoryMock.get(any(Player.class))).thenReturn(new Player(testUsername));
        when(mapMock.getRandomFreeTilePoint()).thenReturn(testPoint);
        when(mapMock.getCoinNo()).thenReturn(testNumberOfCoins);
        when(mapMock.getTile(any(Point.class))).thenReturn(new Tile(0));


        MatchStatus result = service.createMatch(testUsername,testLevelNo);

        verify(matchListSpy).addMatch(any(Match.class));
        Assert.assertTrue(matchListSpy.playerHasMatch(testUsername));
        Assert.assertEquals(result.getId(), matchListSpy.getMatchForPlayer(testUsername).getId());
        Assert.assertEquals(testPoint,
                matchListSpy.getMatchForPlayer(testUsername).getCharacter(testUsername).getPosition());
    }

    @Test
    public void WhenCreatingMatchShouldAssignRandomCharacterAndCoinPositions() throws Exception {
        when(ioServiceMock.getJsonObject(any(String.class))).thenReturn(new JSONObject());
        when(parseServiceMock.parseMap(any(JSONObject.class))).thenReturn(mapMock);
        when(playerRepositoryMock.get(any(Player.class))).thenReturn(new Player(testUsername));
        when(mapMock.getRandomFreeTilePoint()).thenReturn(testPoint);
        when(mapMock.getCoinNo()).thenReturn(testNumberOfCoins);
        when(mapMock.getTile(any(Point.class))).thenReturn(new Tile(0));


        MatchStatus result = service.createMatch(testUsername,testLevelNo);

        verify(mapMock, times(testNumberOfCoins + 1)).getRandomFreeTilePoint();
        Assert.assertEquals(testPoint,
                matchListSpy.getMatchForPlayer(testUsername).getCharacter(testUsername).getPosition());
    }

    @Test
    public void shouldStartMatch() {
        Match matchSpy = spy(new Match(null));
        matchListSpy.addMatch(matchSpy);
        matchSpy.addCharacter(playerMock, testPoint);

        service.startMatch(playerMock);

        verify(matchListSpy, times(1)).getMatchForPlayer(testUsername);
        verify(matchSpy, times(1)).setState(MatchState.Ingame);
        Assert.assertEquals(MatchState.Ingame, matchSpy.getState());
    }

    @Test
    public void shouldGetMatchStatus() {
        Match matchSpy = spy(new Match(null));
        matchSpy.addCharacter(playerMock, testPoint);

        matchListSpy.addMatch(matchSpy);

        MatchStatus result = service.getStatus(playerMock);

        verify(playerMock, atLeastOnce()).getUsername();
        verify(matchListSpy, times(1)).playerHasMatch(testUsername);
        Assert.assertEquals(matchSpy.getId(), result.getId());
    }

    @Test
    public void whenPlayerHasNoMatchGetStatusShouldReturnNull() {
        MatchStatus result = service.getStatus(playerMock);

        Assert.assertNull(result);
    }

    @Test
    public void leaveMatchShouldRemoveCharacterFromMatch() {
        Match matchSpy = spy(new Match(null));
        matchSpy.addCharacter(playerMock, testPoint);

        matchListSpy.addMatch(matchSpy);

        service.leaveMatch(playerMock);

        Assert.assertNull(matchSpy.getCharacter(testUsername));
    }

    @Test
    public void endMatchShouldRemoveMatchFromMatchList() {
        Match matchSpy = spy(new Match(null));
        matchSpy.addCharacter(playerMock, testPoint);

        matchListSpy.addMatch(matchSpy);

        service.endMatch(playerMock);

        verify(matchListSpy, times(1)).removeMatch(matchSpy.getId());
        Assert.assertNull(matchListSpy.getMatchForPlayer(testUsername));
    }

    @Test
    public void joinMatchShoulAddPlayerToMatch() throws Exception {
        when(mapMock.getRandomFreeTilePoint()).thenReturn(testPoint);
        when(playerRepositoryMock.get(any(Player.class))).thenReturn(playerMock);
        Match matchSpy = spy(new Match(mapMock));
        matchListSpy.addMatch(matchSpy);

        service.joinMatch(playerMock, matchSpy.getId());

        Assert.assertTrue(matchSpy.hasCharacter(testUsername));
    }

    @Test
    public void whenSqlExceptionoccursJoinMatchShouldThrowException() throws Exception {
        when(mapMock.getRandomFreeTilePoint()).thenReturn(testPoint);
        when(playerRepositoryMock.get(any(Player.class))).thenThrow(new SQLException());
        Match matchSpy = spy(new Match(mapMock));
        matchListSpy.addMatch(matchSpy);

        try {
            service.joinMatch(playerMock, matchSpy.getId());
            fail();
        }
        catch(SQLException e) {
            //success!
        }
        catch(Exception e) {
            fail();
        }
    }

    @Test
    public void getLobbyingMatchesShouldOnlyReturnMatchesInLobbyState() {
        Match lobbyingMatchMock = mock(Match.class);
        when(lobbyingMatchMock.getState()).thenReturn(MatchState.Lobbying);
        Match inGameMatchMock = mock(Match.class);
        when(inGameMatchMock.getState()).thenReturn(MatchState.Ingame);

        UUID testId = UUID.randomUUID();
        when(lobbyingMatchMock.getId()).thenReturn(testId);

        matchListSpy.addMatch(lobbyingMatchMock);
        matchListSpy.addMatch(inGameMatchMock);

        MatchStatus[] result = service.getLobbyingMatches();

        Assert.assertEquals(1, result.length);
        Assert.assertEquals(testId, result[0].getId());
    }

    @Test
    public void whenNoMatchesInLobbyStateGetLobbyingMatchesShouldReturnEmptyArray() {
        MatchStatus[] result = service.getLobbyingMatches();

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.length);
    }
}
