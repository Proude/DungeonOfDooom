package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The repository to fetch Users.
 */
public class PlayerRepository extends DatabaseRepository<Player> {

    private final String getQuery = "SELECT * FROM User WHERE username = ?";

    @Override
    public void insert(Player object) {

    }

    @Override
    public void delete(Player object) {

    }

    @Override
    public Player get(Player object) throws SQLException {
        PreparedStatement statement = getStatement(getQuery);

        statement.setString(0, object.getName());
        ResultSet rs = statement.executeQuery();

        return new Player(rs.getString(0));
    }
}
