package dod.test.integration.service;

import com.dod.game.MatchList;
import com.dod.models.Player;
import com.dod.models.Point;
import com.dod.service.model.GameStateModel;
import com.dod.service.model.MatchStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the GameController
 */
public class GameControllerTests extends AuthenticatedClientTestBase {

    @Test
    public void shouldRespondToStatus() {
        MatchStatus matchStatus = startNewMatch();
        matchesToRemove.add(matchStatus.getId());

        Invocation.Builder request = target.path("game/status").request();
        request.cookie("JSESSIONID",sessionId);

        Response response = request.buildGet().invoke();

        Assert.assertEquals(200, response.getStatus());
        GameStateModel result = response.readEntity(GameStateModel.class);
        assertNotNull(result);
        assertEquals(1, result.getCharacters().length);
        assertEquals(468, result.getTiles().length);
        assertNotNull(result.getTiles()[0].getPosition());
    }

    @Test
    public void shouldRespondToMove() {
        String responseMsg = target.path("game/move").request().post(null).readEntity(String.class);
        assertEquals("unimplemented", responseMsg);
    }
}
