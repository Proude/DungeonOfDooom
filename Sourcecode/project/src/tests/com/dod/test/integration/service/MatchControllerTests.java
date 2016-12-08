package dod.test.integration.service;

import com.dod.models.MatchState;
import com.dod.service.Main;
import com.dod.service.model.MatchStatus;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Tests for MatchController
 */
public class MatchControllerTests {

    private HttpServer server;
    private WebTarget target;

    private String testUsername;
    private String testPassword;
    private String sessionId;

    @Before
    public void setUp() {
        server = Main.startServer();
        Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");

        MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':');

        final ContextResolver<MoxyJsonConfig> jsonConfigResolver = moxyJsonConfig.resolver();
        Client c = ClientBuilder.newBuilder()
                .register(MoxyJsonFeature.class)
                .register(jsonConfigResolver)
                .build();

        target = c.target(Main.BASE_URI);

        testUsername = UUID.randomUUID().toString();
        testPassword = testUsername;

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", testUsername);
        formData.add("password", testPassword);
        Response registerResponse = target.path("player/register").request().post(Entity.form(formData));
        String registerResponseBody = registerResponse.readEntity(String.class);
        sessionId = registerResponse.getCookies().get("JSESSIONID").getValue();
        int a = 0;
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
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("level", "1");

        javax.ws.rs.client.Invocation.Builder request = target.path("match/new").request();
        request.cookie("JSESSIONID",sessionId);
        Response result = request.post(Entity.form(formData));
        MatchStatus matchStatus = result.readEntity(MatchStatus.class);

        assertEquals(200, result.getStatus());
    }

    @Test
    public void shouldRespondToStart() {
        String responseMsg = target.path("match/start").request().post(null).readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }

    @Test
    public void join() {

    }

    @Test
    public void list() {

    }
}
