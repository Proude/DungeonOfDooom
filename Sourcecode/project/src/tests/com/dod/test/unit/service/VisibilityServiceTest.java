package dod.test.unit.service;

import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Point;
import com.dod.models.Character;
import com.dod.service.service.IOService;
import com.dod.service.service.ParseService;
import com.dod.service.service.VisibilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tasos on 7/12/2016.
 */
public class VisibilityServiceTest {

    private IOService ioService;
    private ParseService parService;
    private Map dungeonMap;
    private JSONObject jobject;
    private Character pChar;
    private Map visibleMap;

    @Before
    public void Setup() {
        ioService = new IOService(".\\assets");
        visibleMap = new Map("level1", 30, 20, 26, 18, new Point(26, 18));
        try {
            jobject = ioService.getJsonObject("\\maps\\Level1.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parService = new ParseService();
        dungeonMap = parService.parseMap(jobject);
        pChar = new Character(new Point(4, 4), new Player("test"));
    }

    @Test
    public void shouldReturnTrueIfTheTile34IsVisible() {
        VisibilityService vService = new VisibilityService();
        visibleMap = vService.createVisibleMap(dungeonMap, pChar);

        Assert.assertTrue(visibleMap.getTile(new Point(3, 4)).isVisible());
    }

    @Test
    public void shouldReturnFalseIfTheTile77IsNotVisible() {
        VisibilityService vService = new VisibilityService();
        visibleMap = vService.createVisibleMap(dungeonMap, pChar);

        Assert.assertFalse(visibleMap.getTile(new Point(7,7)).isVisible());
    }

    @Test
    public void shouldReturnTrueIfTheTile77BecomeVisible() {
        VisibilityService vService = new VisibilityService();
        visibleMap = vService.createVisibleMap(dungeonMap, pChar);

        Assert.assertFalse(visibleMap.getTile(new Point(7, 7)).isVisible());
        pChar.setPosition(new Point(5,5));

        visibleMap = vService.getVisibleTilesForCharacter(visibleMap, dungeonMap, pChar);
        Assert.assertTrue(visibleMap.getTile(new Point(7,7)).isVisible());
    }
}
