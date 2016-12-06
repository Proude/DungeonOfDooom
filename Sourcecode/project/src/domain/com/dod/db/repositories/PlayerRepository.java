package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PlayeRepository is used to establish connection with database
 *  and store, delete or get items from it.
 */
public class PlayerRepository extends DatabaseRepository<Player> implements IPlayerRepository {

    private final String deleteQuery = "DELETE FROM player WHERE username = ?";
    private final String getQuery = "SELECT username, password, salt FROM player WHERE username = ?";
    private final String insertQuery = "INSERT INTO player (username, password, level, salt) VALUES (?, ?, 0, ?)";

    /**
     * Inserts a player info to player table of database.
     * @param object player object
     * @return true if insertion was successful else false
     * @throws SQLException
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

    /**
     * Delete a player row from database
     * !! We should not use that.
     * @param object player object to delete
     * @return true if the deletion was successful else false
     * @throws SQLException
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
     * returns a player based on username from the database
     * @param object player object
     * @return player object
     * @throws SQLException
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
}
