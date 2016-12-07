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

    IOService ioService;
    ParseService parService;
    private Map dungeonMap;
    JSONObject jobject;
    Character pChar;

    @Before
    public void Setup() {
        ioService = new IOService();
        try {
            jobject = ioService.getJsonObject("\\maps\\level1.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parService = new ParseService();
        dungeonMap = parService.parseMap(jobject);
        pChar = new Character(new Point(4, 4), new Player("test"), 0);
    }

    @Test
    public void shouldReturnTrueIfTheTile34IsVisiblie() {
        VisibilityService vService = new VisibilityService();
        Map visibleMap = vService.getVisibleTilesForCharacter(dungeonMap, pChar);

        Assert.assertTrue(visibleMap.getTile(new Point(3, 4)).getVisibility());
    }
}
