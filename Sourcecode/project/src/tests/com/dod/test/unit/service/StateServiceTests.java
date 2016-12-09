package dod.test.unit.service;

import com.dod.game.MatchList;
import com.dod.models.*;
import com.dod.service.model.GameStateModel;
import com.dod.service.service.IOService;
import com.dod.service.service.IVisibilityService;
import com.dod.service.service.ParseService;
import com.dod.service.service.StateService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Tests for the StateService.
 */
public class StateServiceTests {

    private IVisibilityService visibilityServiceMock;
    private MatchList matchListMock;
    private StateService stateService;
    private Map map;
    private Point testPoint;
    private Match match;

    private String testUsername = "testUsername";

    @Before
    public void Setup() {
        visibilityServiceMock = mock(IVisibilityService.class);
        matchListMock = mock(MatchList.class);

        stateService = new StateService(visibilityServiceMock, matchListMock);

        IOService ioService = new IOService();
        JSONObject jobject = null;
        try {
            jobject = ioService.getJsonObject("\\maps\\level1.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseService parService = new ParseService();
        map = parService.parseMap(jobject);

        match = new Match(map);
        testPoint = map.getRandomFreeTilePoint();
        match.addCharacter(new Player(testUsername), testPoint);

        when(matchListMock.getMatchForPlayer(testUsername)).thenReturn(match);
    }

    @Test
    public void shouldGetCurrentStateOfGame() {
        GameStateModel result = stateService.GetState(new Player(testUsername));
        when(visibilityServiceMock.getVisibleTilesForCharacter(map, match.getCharacter(testUsername))).thenReturn(map);

        Assert.assertEquals(testPoint, result.getCharacters()[0].getPosition());
        Assert.assertEquals(map.getWidth() * map.getHeight(), result.getTiles().length);
        Assert.assertEquals(1, result.getCharacters().length);
    }

    @Test
    public void shouldOnlyReturnVisibleTiles() {
        when(visibilityServiceMock.getVisibleTilesForCharacter(map, match.getCharacter(testUsername))).thenReturn(null);
        //todo
        fail();
    }
}