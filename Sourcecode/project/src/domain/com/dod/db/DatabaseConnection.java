package com.dod.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Stores a connection to the database using the singleton pattern
 */
public class DatabaseConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection != null) {
            return connection;
        }
        else {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("dungeonofdoom");
            dataSource.setPassword("Delicate.Sunshine.Twist.Myth32");
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("dungeonofdoom");

            connection = dataSource.getConnection();

            return connection;
        }
    }

    public static void Close() {
        try {
            connection.close();
        }
        catch(SQLException e) {
            System.console().printf(e.getMessage());
        }
    }
}