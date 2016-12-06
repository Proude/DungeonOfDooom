package dod.test.integration.service;

import com.dod.db.repositories.PlayerRepository;
import com.dod.models.Player;
import com.dod.service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.assertEquals;

/**
 * Tests the PlayerController
 */
public class PlayerControllerTests {

    private HttpServer server;
    private WebTarget target;
    private PlayerRepository repository;

    private final String testUsername = "testUsername";
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
    public void shouldRespondToLogin() {
        String responseMsg = target.path("player/login").request().post(null).readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }

    @Test
    public void whenDetailsAreValidShouldRegisterPlayer() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", testPassword);

        String responseMsg = target.path("player/register")
                .request()
                .post(Entity.form(formData))
                .readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }

}
