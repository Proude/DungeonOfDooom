package dod.test.integration.db;

import com.dod.db.repositories.PlayerRepository;
import com.dod.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 *
 */
public class DatabaseQueryTests {

    @Test
    public void shouldReturnTrueIfNewValueIsInDatabase()
    {
        PlayerRepository pr = new PlayerRepository();
        Player pl = new Player("Qian", "sha1(\'1234\')");
        try {
            pr.insert(pl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(pl.getUsername() + " " + pl.getPassword());
            System.out.println(pr.get(pl).getUsername() + " " + pr.get(pl).getPassword());
            Assert.assertTrue(pl.getUsername().equals(pr.get(pl).getUsername()) && pl.getPassword().equals(pr.get(pl).getPassword()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
