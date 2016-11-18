package com.dod.tests.integration;

import com.dod.db.DatabaseConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Tests database integration
 */
public class database {

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