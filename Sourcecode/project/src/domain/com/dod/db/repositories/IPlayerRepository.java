package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.SQLException;

/**
 * <pre>
 *     Follows the Repository pattern.
 *     Intended for selecting/inserting/deleting "Player" entries from the database.
 * </pre>
 */
public interface IPlayerRepository {
    /**
     * Make a SELECT query to fetch the unique Player in question from the database
     * @param object an instance of the Player in question with the unique field (but not necessarily others) filled out
     * @return Player object fetched from the database
     * @throws SQLException if the statement fails or connection cannot be established
     */
    Player get(Player object) throws SQLException;
    /**
     * Make an INSERT query to insert the Player in question into the database
     * @param object the Player in question
     * @return true if successful, false otherwise
     * @throws SQLException when the statement fails
     */
    boolean delete(Player object) throws SQLException;
    /**
     * Make a DELETE query to delete the Player in question from the database
     * @param object the Player in question with the unique field (but not necessarily others) filled out
     * @return true if successful, false otherwise
     * @throws SQLException when the statement fails
     */
    boolean insert(Player object) throws SQLException;
}
