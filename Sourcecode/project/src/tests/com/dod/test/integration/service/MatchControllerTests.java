package dod.test.integration.service;

import com.dod.service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

/**
 * Tests for MatchController
 */
public class MatchControllerTests {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldRespondToStatus() {
        String responseMsg = target.path("match/status").request().get(String.class);
        assertEquals("unimplemented", responseMsg);
    }

    @Test
    public void shouldRespondToNew() {
        String responseMsg = target.path("match/new").request().post(null).readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }

    @Test
    public void shouldRespondToStart() {
        String responseMsg = target.path("match/start").request().post(null).readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }
}
