package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The repository to fetch Users.
 */
public class PlayerRepository extends DatabaseRepository<Player> {

    private final String getQuery = "SELECT username, password FROM player WHERE username = ?";
    private final String insertQuery = "INSERT INTO player (username, password, level) VALUES (?, ?, 0)";

    @Override
    public void insert(Player object) throws SQLException{

        PreparedStatement statement = this.getStatement(insertQuery);

        statement.setString(1, object.getUsername());
        statement.setString(2, object.getPassword());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(Player object) {

    }

    @Override
    public Player get(Player object) throws SQLException {
        PreparedStatement statement = this.getStatement(getQuery);

        statement.setString(1, object.getUsername());
        ResultSet rs = statement.executeQuery();
        rs.next();

        return new Player(rs.getString("username"), rs.getString("password"));
    }
}
