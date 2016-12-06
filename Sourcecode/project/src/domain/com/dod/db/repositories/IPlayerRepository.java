package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.SQLException;

/**
 * An interface to make testing components that depend on PlayerRepository easier
 */
public interface IPlayerRepository {
    Player get(Player object) throws SQLException;
    boolean delete(Player object) throws SQLException;
    boolean insert(Player object) throws SQLException;
}
