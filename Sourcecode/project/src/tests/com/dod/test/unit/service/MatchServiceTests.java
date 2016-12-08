package dod.test.unit.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.game.IMatchList;
import com.dod.game.MatchList;
import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Point;
import com.dod.service.model.MatchStatus;
import com.dod.service.service.IIOService;
import com.dod.service.service.IOService;
import com.dod.service.service.IParseService;
import com.dod.service.service.MatchService;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for MatchService
 */
public class MatchServiceTests {

    MatchService service;

    IIOService ioServiceMock;
    IParseService parseServiceMock;
    IPlayerRepository playerRepositoryMock;
    IMatchList matchListMock;
    Map mapMock;

    private final int testLevelNo = 0;
    private final String testLevelPath = "/maps/level0.json";
    private final String testUsername = "testUsername";
    private final Point testPoint = new Point(0,0);

    @Before
    public void setup() {
        ioServiceMock = mock(IOService.class);
        parseServiceMock = mock(IParseService.class);
        playerRepositoryMock = mock(IPlayerRepository.class);
        matchListMock = mock(IMatchList.class);
        mapMock = mock(Map.class);

        service = new MatchService(ioServiceMock, parseServiceMock, playerRepositoryMock, matchListMock);
    }

    @Test
    public void shouldCreateMatchAndAssignRandomCharacterPosition() throws Exception {
        when(ioServiceMock.getJsonObject(any(String.class))).thenReturn(new JSONObject());
        when(parseServiceMock.parseMap(any(JSONObject.class))).thenReturn(mapMock);
        when(playerRepositoryMock.get(any(Player.class))).thenReturn(new Player(testUsername));
        when(mapMock.getRandomFreeTilePoint()).thenReturn(new Point(0,0));

        MatchStatus result = service.createMatch(testUsername,testLevelNo);

        //Assert.assertTrue(MatchList.playerHasMatch(testUsername));
        //Assert.assertEquals(result.getId(), MatchList.getMatchForPlayer(testUsername).getId());
        //Assert.assertEquals(testPoint,
          //      MatchList.getMatchForPlayer(testUsername).getCharacter(testUsername).getPosition());
    }

    @Test
    public void shouldStartMatch() {

    }

    @Test
    public void shouldGetMatchStatus() {

    }

    @Test
    public void whenPlayerHasNoMatchGetStatusShouldReturnEmptyMatchStatus() {

    }

    @Test
    public void leaveMatchShouldRemoveCharacterFromMatch() {

    }

    @Test
    public void endMatchShouldRemoveMatchFromMatchList() {
        //todo think about this not clear how it should work
    }

    @Test
    public void joinMatchShoulAddPlayerToMatch() {

    }

    @Test
    public void getLobbyingMatchesShouldOnlyReturnMatchesInLobbyState() {

    }

    @Test
    public void whenNoMatchesInLobbyStateGetLobbyingMatchesShouldReturnEmptyArray() {

    }
}
