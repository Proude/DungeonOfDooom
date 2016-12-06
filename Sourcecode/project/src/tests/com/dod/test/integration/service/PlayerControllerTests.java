package dod.test.integration.service;

import com.dod.db.repositories.PlayerRepository;
import com.dod.models.Player;
import com.dod.service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the PlayerController
 */
public class PlayerControllerTests {

    private HttpServer server;
    private WebTarget target;
    private PlayerRepository repository;

    private final String testUsername = "testUsername";
    private final String testNonExistantusername = "testNonexistantUsername";
    private final String testPassword = "testPassword";

    @Before
    public void setUp() {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        repository = new PlayerRepository();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();

        try {
            repository.delete(new Player(testUsername));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDetailsAreValidShouldRegisterPlayer() throws Exception {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", testPassword);

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals("", response.readEntity(String.class));
        assertEquals(200, response.getStatus());
        assertNotNull(repository.get(new Player(testUsername)));
    }

    @Test
    public void whenUsernameEmptyRegisterShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", "");
        formData.add("password", testPassword);

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenPasswordEmptyRegisterShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", "");

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenPasswordTooLongRegisterShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", generateStringOfSize(257));

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenUsernameTooLongRegisterShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", generateStringOfSize(256));
        formData.add("password", testPassword);

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenUsernameAlreadyTakenRegisterShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", testPassword);

        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(200, response.getStatus());

        response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenDetailsValidLoginShouldReturnBlankOkStatus() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", testPassword);

        //Create player before trying to login
        Response response = target.path("player/register")
                .request()
                .post(Entity.form(formData));

        assertEquals(200, response.getStatus());

        response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        Assert.assertEquals(200, response.getStatus());
        assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void whenUsernameEmptyLoginShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", "");
        formData.add("password", testPassword);

        Response response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenPasswordEmptyLoginShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", "");

        Response response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenPasswordTooLongLoginShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", generateStringOfSize(256));

        Response response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void whenUsernameTooLongLoginShouldReturnValidationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", generateStringOfSize(256));
        formData.add("password", testPassword);

        Response response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(400, response.getStatus());
    }

    /**
     * We don't want to return validation here- we don't want to inform a malicious user
     * when they do or don't randomly guess a correct username
     */
    @Test
    public void whenUsernameDoesNotExistLoginShouldReturnBlankAuthorisationError() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testNonExistantusername);
        formData.add("password", testPassword);

        Response response = target.path("player/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(403, response.getStatus());
        assertEquals("", response.readEntity(String.class));
    }

    private String generateStringOfSize(int size) {
        String result = "";

        for(int i = 0; i < size; i++) {
            result += "z";
        }

        return result;
    }
}
