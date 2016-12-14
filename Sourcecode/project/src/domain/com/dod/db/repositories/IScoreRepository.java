package com.dod.db.repositories;

import com.dod.models.Player;
import com.dod.models.Score;

import java.sql.SQLException;

/**
 * <pre>
 *     Follows the Repository pattern.
 *     Intended for selecting/inserting/deleting "Score" entries from the database.
 * </pre>
 */
public interface IScoreRepository {
    /**
     * Make a SELECT query to fetch the unique Score in question from the database
     * @param object an instance of the Score in question with the unique field (but not necessarily others) filled out
     * @return Score fetched from the database
     * @throws SQLException if the statement fails or connection cannot be established
     */
    Score get(Score object) throws SQLException;
    /**
     * Make an INSERT query to insert the Score in question into the database
     * @param object the Score in question
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    boolean insert(Score object) throws SQLException;
    /**
     * Make a DELETE query to delete the Score in question from the database
     * @param object the Score in question with the unique field (but not necessarily others) filled out
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    boolean delete(Score object) throws SQLException;

    /**
     * Get the 10 highest scores from database
     * @return Score[] array of 10 Score objects
     * @throws SQLException when the statement fails
     */
    Score[] getHighestScores() throws SQLException;

    /**
     * Get the 10 highest scores of the player
     * @param object Player object
     * @return Score[] array of 10 Score objects
     * @throws SQLException when the statement fails
     */
    Score[] getPlayerScores(Player object) throws SQLException;
}
