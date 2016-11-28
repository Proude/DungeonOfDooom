package com.dod.db.repositories;

import com.dod.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A base class of the Repository pattern
 */
public class DatabaseRepository<T> {

    public void insert(T object) throws SQLException  {    }
    public void delete(T object) throws SQLException {     }
    public T get(T object) throws SQLException { return  null;  }

    protected PreparedStatement ps;

    protected PreparedStatement getStatement(String text) throws SQLException
    {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(text);

        return ps;
    }

}
