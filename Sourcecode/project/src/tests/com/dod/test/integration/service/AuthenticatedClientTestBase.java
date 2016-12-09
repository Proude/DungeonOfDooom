package dod.test.integration.service;

import com.dod.game.MatchList;
import com.dod.service.Main;
import com.dod.service.model.MatchStatus;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * A base class for testing endpoints with sessions
 */
public class AuthenticatedClientTestBase {
    protected WebTarget target;
    protected String testUsername;
    protected String sessionId;
    protected List<UUID> matchesToRemove;
    private HttpServer server;

    @Before
    public void setUp() {
        server = Main.startServer();

        //Setup JSON client
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

        //Generate random user/pass for testing
        testUsername = UUID.randomUUID().toString();

        //Register user/pass so we have a guarunteed user that exists
        sessionId = registerUserAndGetSessionId(testUsername);
        //For cleaning up the static MatchList
        matchesToRemove = new ArrayList();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();

        //Cleanup static data before next turn
        for(UUID id : matchesToRemove) {
            MatchList.instance().removeMatch(id);
        }
    }

    protected String registerUserAndGetSessionId(String identifier) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", identifier);
        formData.add("password", identifier);
        Response registerResponse = target.path("player/register").request().post(Entity.form(formData));

        //get the sessionId so we can send authorised session cookies with requests
        return registerResponse.getCookies().get("JSESSIONID").getValue();
    }

    protected MatchStatus startNewMatch() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("level", "1");

        javax.ws.rs.client.Invocation.Builder request = target.path("match/new").request();
        request.cookie("JSESSIONID",sessionId);
        Response result = request.post(Entity.form(formData));
        assertEquals(200, result.getStatus());

        return result.readEntity(MatchStatus.class);
    }
}
