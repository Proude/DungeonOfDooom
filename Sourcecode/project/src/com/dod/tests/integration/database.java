package com.dod.tests.integration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Tests database integration
 */
public class database {

    @Test
    //todo: replace this with actual database connection classes once we get that far
    public void TestBasicConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("dungeonofdoom");
        dataSource.setPassword("Delicate.Sunshine.Twist.Myth32");
        dataSource.setServerName("localhost");

        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");

            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}