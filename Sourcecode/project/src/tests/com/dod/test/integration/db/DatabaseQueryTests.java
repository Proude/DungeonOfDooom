package dod.test.integration.db;

import com.dod.db.repositories.PlayerRepository;
import com.dod.db.repositories.ScoreRepository;
import com.dod.models.Player;
import com.dod.models.Score;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Unit tests for Database
 */
public class DatabaseQueryTests {

    @Test
    public void shouldReturnTrueIfNewPlayerValueIsAddedInDatabase() {
        PlayerRepository pr = new PlayerRepository();
        String pass = DigestUtils.sha1Hex("1234");
        Player pl = new Player("test", pass, new byte[0]);
        try {
            Assert.assertTrue(pr.insert(pl));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueIfPlayerValueExistsInDatabase() {

        PlayerRepository pr = new PlayerRepository();
        String pass = DigestUtils.sha1Hex("1234");
        Player pl = new Player("test", pass, new byte[0]);
        try {
            Assert.assertTrue(pl.getUsername().equals(pr.get(pl).getUsername()) && pl.getHashedPassword().equals(pr.get(pl).getHashedPassword()));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueIfPlayerValueIsDeleted() {
        PlayerRepository pr = new PlayerRepository();
        String pass = DigestUtils.sha1Hex("1234");
        Player pl = new Player("test", pass, new byte[0]);
        try {
            Assert.assertTrue(pr.delete(pl));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueIfNewScoreValueIsAdded() {
        ScoreRepository pr = new ScoreRepository();
        Player nPlayer = new Player("test", "1234", new byte[0]);
        Score temp = new Score(nPlayer.getUsername(), 20);
        try {
            Assert.assertTrue(pr.insert(temp));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueIfScoreValueExistsInDatabase() {
        ScoreRepository pr = new ScoreRepository();
        Score temp = new Score(1, "test", 20);
        try {
            Assert.assertTrue(temp.getId() == pr.get(temp).getId() && temp.getValue() == pr.get(temp).getValue() && temp.getUsername().equals(pr.get(temp).getUsername()));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTrueIfScoreValueIsDeleted() {
        ScoreRepository pr = new ScoreRepository();
        Score temp = new Score(1, "test", 20);
        try {
            Assert.assertTrue(pr.delete(temp));
        } catch (SQLException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

}
