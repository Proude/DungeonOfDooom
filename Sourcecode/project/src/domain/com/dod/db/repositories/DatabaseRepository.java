package com.dod.db.repositories;

import com.dod.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <pre>
 * A base class of the Repository pattern
 * Introduces the generic getStatement() method to reuse that code across the different repositories
 * </pre>
 */
public class DatabaseRepository<T> {
    /**
     * Make a SELECT query to fetch the unique object in question from the database
     * @param object an instance of the object in question with the unique field (but not necessarily others) filled out
     * @return An instance of the object
     * @throws SQLException if the statement fails or connection cannot be established
     */
    public T get(T object) throws SQLException { return  null;  }

    /**
     * Make an INSERT query to insert the object in question into the database
     * @param object the object in question
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    public boolean insert(T object) throws SQLException  {  return false;  }

    /**
     * Make a DELETE query to delete the object in question from the database
     * @param object the object in question with the unique field (but not necessarily others) filled out
     * @return true if successful, false otherwise
     * @throws SQLException when the statement fails
     */
    public boolean delete(T object) throws SQLException {  return false;   }

    protected PreparedStatement ps;

    /**
     * Prepares a statement from a string using the database connection
     * @param text the text of the statement
     * @return a PreparedStatement instance
     * @throws SQLException when the statement fails
     */
    protected PreparedStatement getStatement(String text) throws SQLException
    {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(text);

        return ps;
    }

}
