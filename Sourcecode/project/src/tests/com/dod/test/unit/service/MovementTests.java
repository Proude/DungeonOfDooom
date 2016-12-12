package dod.test.unit.service;

import com.dod.models.Character;
import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Point;
import com.dod.service.service.IOService;
import com.dod.service.service.MovementService;
import com.dod.service.service.ParseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tasos on 11/12/2016.
 */
public class MovementTests {

    private IOService ioService;
    private ParseService parService;
    private Map dungeonMap;
    private Character pChar, pChar2;
    private JSONObject jobject;

    @Before
    public void Setup() {
        ioService = new IOService(".\\assets");
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
        pChar2 = new Character(new Point(3, 1), new Player("dadasda"));
    }

    @Test
    public void shouldReturnTrueIfPlayerMovedToRightTile() {
        MovementService moveService = new MovementService();
        Assert.assertTrue(moveService.Move("D", new Player("test")).equals(new Point(5,4)));
    }

    @Test
    public void shouldReturnFalseIfPlayerMovedToRightTile() {
        MovementService moveService = new MovementService();
        Assert.assertFalse(moveService.Move("D", new Player("test")).equals(new Point(4,4)));
    }

    @Test
    public void shouldReturnFalseIfPlayerMovesToWall() {
        MovementService moveService = new MovementService();
        Assert.assertFalse(moveService.Move("D", new Player("test")).equals(new Point(3,0)));
    }

    @Test
    public void shouldReturnTrueIfPlayerCantMoveToWall() {
        MovementService moveService = new MovementService();
        Assert.assertFalse(moveService.Move("D", new Player("test")).equals(new Point(3,1)));
    }
}
