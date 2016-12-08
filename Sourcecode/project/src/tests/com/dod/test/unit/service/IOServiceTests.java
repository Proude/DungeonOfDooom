package dod.test.unit.service;

import com.dod.service.constant.Assets;
import com.dod.service.service.IIOService;
import com.dod.service.service.IOService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit tests for the IOService
 */
public class IOServiceTests {

    IIOService service;

    private String testAssetPath = "\\test\\test.asset";
    private String expectedTestAssetResult = "testasset :)";
    private String nonExistantTestAssetPath = "nonexistant.asset";
    private String testJsonPath = "\\test\\test.json";

    @Before
    public void Setup() {
        service = new IOService(".\\assets");
    }

    @Test
    public void shouldGetAssetAtPath() {
        try {
            String result = service.getString(testAssetPath);
            Assert.assertEquals(expectedTestAssetResult, result);
        }
        catch(IOException e) {
            Assert.fail("Unexepected exception thrown by service:" + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void whenPathIsInvalidShouldThrowException() {
        try {
            String result = service.getString(nonExistantTestAssetPath);
            Assert.fail("Service did not throw exception when expected.");
        }
        catch(IOException e) {
            //Pass!
        }
    }

    @Test
    public void shouldParseJsonFile() {
        try {
            JSONObject result = service.getJsonObject(testJsonPath);
            Assert.assertTrue(result.containsKey("id"));
        }
        catch(Exception e) {
            Assert.fail("Unexepected exception thrown by service:" + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void whenJsonIsInvalidShouldThrownParseException() {
        try {
            JSONObject result = service.getJsonObject(testAssetPath);
            Assert.fail("Service did not throw exception when expected.");
        }
        catch(ParseException e) {
            //Pass!
        }
        catch(Exception e) {
            Assert.fail("Unexepected exception thrown by service:" + e.toString());
            e.printStackTrace();
        }
    }
}