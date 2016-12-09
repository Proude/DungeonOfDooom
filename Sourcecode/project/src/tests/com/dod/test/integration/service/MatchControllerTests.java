package dod.test.integration.service;

import com.dod.game.MatchList;
import com.dod.models.Match;
import com.dod.models.MatchState;
import com.dod.service.Main;
import com.dod.service.model.MatchStatus;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for MatchController
 * !NOTE! : As of right now these tests will ONLY work if you add a Symboolic Link directory (mklink /d in Win)
 * to the git root pointing to the Assets folder
 * This is becaues of project config issues... It's a crap solution I know.
 * Potential future solutions:
 *      Add a run parameter that can override the assets folder path
 *      Place a static variable somewhere that can be overridden by the Tests project, to hold the assets folder path.
 *      Find a way to pass a variable into the HttpServer object that can be fed to the IOService
 */
public class MatchControllerTests {

    private HttpServer server;
    private WebTarget target;

    private String testUsername;
    private String sessionId;

    private List<UUID> matchesToRemove;

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

    @Test
    public void shouldGiveCurrentMatchStatus() {
        MatchStatus matchStatus = startNewMatch();

        Invocation.Builder request = target.path("match/status").request();
        request.cookie("JSESSIONID",sessionId);
        MatchStatus response = request.get(MatchStatus.class);

        assertNotNull(response);
        matchesToRemove.add(matchStatus.getId());
        assertEquals(matchStatus.getId(), response.getId());
        assertEquals(testUsername, response.getPlayerNames()[0]);
    }

    @Test
    public void whenPlayerHasNoOngoingMatchStatusShouldReturnNull() {
        Invocation.Builder request = target.path("match/status").request();
        request.cookie("JSESSIONID",sessionId);
        MatchStatus response = request.get(MatchStatus.class);

        assertNull(response);
    }

    @Test
    public void shouldCreateNewMatch() {
        MatchStatus matchStatus = startNewMatch();
        matchesToRemove.add(matchStatus.getId());

        assertNotNull(matchStatus.getId());
        matchesToRemove.add(matchStatus.getId());

        assertEquals(testUsername, matchStatus.getPlayerNames()[0]);
        assertNotNull(MatchList.instance().getMatch(matchStatus.getId()));
    }

    @Test
    public void shouldStartMatch() {
        MatchStatus matchStatus = startNewMatch();
        matchesToRemove.add(matchStatus.getId());

        Invocation.Builder request = target.path("match/start").request();
        request.cookie("JSESSIONID",sessionId);
        Response result = request.post(null);

        assertEquals(200, result.getStatus());
        assertEquals(MatchState.Ingame, MatchList.instance().getMatch(matchStatus.getId()).getState());
    }

    @Test
    public void join() {
        //Add a match with the original test user
        MatchStatus matchStatus = startNewMatch();
        matchesToRemove.add(matchStatus.getId());
        //Register another user that isn't already a member of the new match
        String newTestUsername = UUID.randomUUID().toString();
        String newUserSession = registerUserAndGetSessionId(newTestUsername);

        Invocation.Builder request = target.path("match/join").request();
        request.cookie("JSESSIONID",newUserSession);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("matchId", matchStatus.getId().toString());
        Response result = request.post(Entity.form(formData));

        assertEquals(200, result.getStatus());
        Assert.assertTrue(MatchList.instance().getMatch(matchStatus.getId()).hasCharacter(newTestUsername));
    }

    //todo improve this test so that it doesn't break other tests if it fails
    @Test
    public void list() {
        MatchList.instance().addMatch(new Match(null));
        MatchList.instance().addMatch(new Match(null));
        MatchList.instance().addMatch(new Match(null));

        Invocation.Builder request = target.path("match/list").request();
        request.cookie("JSESSIONID",sessionId);
        Response result = request.get();
        MatchStatus[] response = result.readEntity(MatchStatus[].class);

        assertEquals(200, result.getStatus());
        assertEquals(3, response.length);

        matchesToRemove.add(response[0].getId());
        matchesToRemove.add(response[1].getId());
        matchesToRemove.add(response[2].getId());
    }

    private MatchStatus startNewMatch() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("level", "1");

        javax.ws.rs.client.Invocation.Builder request = target.path("match/new").request();
        request.cookie("JSESSIONID",sessionId);
        Response result = request.post(Entity.form(formData));
        assertEquals(200, result.getStatus());

        return result.readEntity(MatchStatus.class);
    }

    private String registerUserAndGetSessionId(String identifier) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", identifier);
        formData.add("password", identifier);
        Response registerResponse = target.path("player/register").request().post(Entity.form(formData));

        //get the sessionId so we can send authorised session cookies with requests
        return registerResponse.getCookies().get("JSESSIONID").getValue();
    }
}
