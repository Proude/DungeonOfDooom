package dod.test.unit.domain.model;

import com.dod.models.*;
import com.dod.models.Character;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Tests some of the non-trivial Match functions
 */
public class MatchTests {

    Map map;
    Match match;
    Player player;

    private final String testUsername = "testUsername";

    @Before
    public void Setup() {
        map = mock(Map.class);
        match = new Match(map);
        player = mock(Player.class);
        when(player.getUsername()).thenReturn(testUsername);
    }

    @Test
    public void shouldAddCharacter() {
        match.addCharacter(player, new Point(0,0));
        Assert.assertTrue(match.hasCharacter(testUsername));
    }

    @Test
    public void whenThereAreMultipleCharactersShouldGetCorrectCharacter() {
        Player anotherPlayer = mock(Player.class);
        Player anotherAnotherPlayer = mock(Player.class);
        when(anotherPlayer.getUsername()).thenReturn("anotherTestUsername");
        when(anotherAnotherPlayer.getUsername()).thenReturn("anotherAnotherTestUsername");

        match.addCharacter(player, new Point(0,0));
        match.addCharacter(anotherPlayer, new Point(0,0));
        match.addCharacter(anotherAnotherPlayer, new Point(0,0));

        Assert.assertEquals(player, match.getCharacter(testUsername).getPlayer());
    }
}
