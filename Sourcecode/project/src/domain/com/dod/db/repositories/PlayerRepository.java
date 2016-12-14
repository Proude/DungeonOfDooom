package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 *     Implements IPlayerRepository.
 *     Follows the Repository pattern.
 *     Intended for selecting/inserting/deleting "Player" entries from the database.
 * </pre>
 */
public class PlayerRepository extends DatabaseRepository<Player> implements IPlayerRepository {

    private final String deleteQuery = "DELETE FROM player WHERE username = ?";
    private final String getQuery = "SELECT username, password, salt FROM player WHERE username = ?";
    private final String insertQuery = "INSERT INTO player (username, password, level, salt) VALUES (?, ?, 0, ?)";

    /**
     * Make a SELECT query to fetch the unique Player in question from the database
     * @param object an instance of the Player in question with the unique field (but not necessarily others) filled out
     * @return Player object fetched from the database
     * @throws SQLException if the statement fails or connection cannot be established
     */
    @Override
    public Player get(Player object) throws SQLException {
        PreparedStatement statement = getStatement(getQuery);

        statement.setString(1, object.getUsername());
        ResultSet rs = statement.executeQuery();
        if (rs.next())
            return new Player(rs.getString("username"), rs.getString("password"), rs.getBytes("salt"));
        else
            return null;
    }

    /**
     * Make an INSERT query to insert the Player in question into the database
     * @param object the Player in question
     * @return true if successful, false otherwise
     * @throws SQLException when the statement fails
     */
    @Override
    public boolean delete(Player object) throws SQLException {
        PreparedStatement statement = getStatement(deleteQuery);

        statement.setString(1, object.getUsername());
        if (statement.executeUpdate() == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Make a DELETE query to delete the Player in question from the database
     * @param object the Player in question with the unique field (but not necessarily others) filled out
     * @return true if successful, false otherwise
     * @throws SQLException when the statement fails
     */
    @Override
    public boolean insert(Player object) throws SQLException{

        PreparedStatement statement = getStatement(insertQuery);

        statement.setString(1, object.getUsername());
        statement.setString(2, object.getHashedPassword());
        statement.setBytes(3, object.getSalt());
        try {
            statement.executeUpdate();
        }
        catch (SQLException e) {
            return false;
        }
        statement.close();
        return true;
    }
}
