package dod.test.unit.service;

import com.dod.models.Map;
import com.dod.models.Point;
import com.dod.service.service.IParseService;
import com.dod.service.service.ParseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ParseService
 */
public class ParseServiceTests {

    private IParseService service;
    private JSONParser parser;

    private String validJson =
            "{ \"testLev\": { \"id\":\"test\", \"name\":\"test\",\"coin_num\":6, \"coin_win\":5, \"Width\" : 26, \"Height\" : 18, \"tiles\": [" +
            "{\"id\": \"tile_wall\",\"name\":\"wall\",\"type\":0,\"visibility\":true,\"touchable\":false}," +
            "{\"id\": \"tile_path\",\"name\":\"path\",\"type\":1,\"visibility\":true,\"touchable\":true}," +
            "{\"id\": \"tile_path2\",\"name\":\"path2\",\"type\":2,\"visibility\":true,\"touchable\":true}" +
			"], \"map\":[ [{\"type\":0},{\"type\":0},{\"type\":0},{\"type\":0}], " +
            "[{\"type\":0},{\"type\":0},{\"type\":0},{\"type\":0}]] } }";

    private String invalidJson =
            "{ \"testLev\": { \"id\":\"test\", \"tiles\": [" +
            "{\"id\": \"tile_wall\",\"name\":\"wall\",\"type\":0,\"visibility\":true,\"touchable\":false}," +
            "{\"id\": \"tile_path\",\"name\":\"path\",\"type\":1,\"visibility\":true,\"touchable\":true}," +
            "{\"id\": \"tile_path2\",\"name\":\"path2\",\"type\":2,\"visibility\":true,\"touchable\":true}" +
            "], \"map\":[ [{\"type\":0},{\"type\":0},{\"type\":0},{\"type\":0}], " +
            "[{\"type\":0},{\"type\":0},{\"type\":0},{\"type\":0}]] } }";

    @Before
    public void Setup() {
        service = new ParseService();
        parser = new JSONParser();
    }

    @Test
    public void shouldGenerateMapFromJson() {
        try {
            JSONObject input = (JSONObject) parser.parse(validJson);
            Map result = service.parseMap(input);

            Assert.assertEquals(5, result.getCoinWin());
            Assert.assertEquals(6, result.getCoinNo());
            Assert.assertEquals("test", result.getName());

            for(int x = 0; x < 4; x++) {
                for(int y = 0; y < 2; y++) {
                    Assert.assertEquals(0, result.getTile(new Point(x,y)).getType());
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Assert.fail("Unexepected exception thrown by service:" + e.toString());
        }
    }

    @Test
    public void whenJsonIsInvalidShouldThrowException() {
        try {
            JSONObject input = (JSONObject) parser.parse(invalidJson);
            Map result = service.parseMap(input);
            Assert.fail("Test did not throw expected exception.");
        }
        catch(NullPointerException e) {
            //Passed!
        }
        catch(Exception e) {
            Assert.fail("Unexepected exception thrown by service:" + e.toString());
            e.printStackTrace();
        }
    }
}
