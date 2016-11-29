package dod.test.integration;

import com.dod.db.DatabaseConnection;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tests database integration
 */
public class DatabaseConnectionTests {

    @Test
    public void ShouldConnectToDatabase() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            Assert.assertFalse(connection.isClosed());
        }
        catch(SQLException e) {
            Assert.fail(e.getMessage());
        }
        DatabaseConnection.Close();
    }

    @Test
    public void ShouldCloseDatabase() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
        }
        catch(SQLException e) {
            Assert.fail(e.getMessage());
        }

        DatabaseConnection.Close();

        try {
            Assert.assertTrue(connection.isClosed());
        }
        catch(SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

}