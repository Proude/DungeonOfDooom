package com.dod.db.repositories;

import com.dod.models.Player;
import com.dod.models.Score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 *     Implements IPlayerRepository.
 *     Follows the Repository pattern.
 *     Intended for selecting/inserting/deleting "Score" entries from the database.
 * </pre>
 */
public class ScoreRepository extends DatabaseRepository<Score> implements IScoreRepository {

    private final String getPlayerQuery = "SELECT * FROM score WHERE username='?' ORDER BY value DESC LIMIT 10";
    private final String deleteQuery = "DELETE FROM score WHERE id = ?";
    private final String getScoreQuery = "SELECT * FROM score WHERE id = ?";
    private final String getQuery = "SELECT * FROM score ORDER BY value DESC LIMIT 10";
    private final String insertQuery = "INSERT INTO score (username, value) VALUES (?, ?)";

    /**
     * Inserts a score value to score table of database based on player's
     * username.
     * @param scoreObject current score that we need to score
     * @return true if insertion was successful else false
     * @throws SQLException when the statement fails
     */
    @Override
    public boolean insert(Score scoreObject) throws SQLException {

        PreparedStatement statement = getStatement(insertQuery);

        statement.setString(1, scoreObject.getUsername());
        statement.setInt(2, scoreObject.getValue());

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        statement.close();
        return true;
    }

    /**
     * Delete a score row from database
     * !! We should not use that.
     * @param object score object to delete
     * @return true if the deletion was successful else false
     * @throws SQLException when the statement fails
     */
    @Override
    public boolean delete(Score object) throws SQLException {
        PreparedStatement statement = getStatement(deleteQuery);

        statement.setInt(1, object.getId());
        if (statement.executeUpdate() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the 10 highest scores from database
     * @return Score[] array of 10 Score objects
     * @throws SQLException when the statement fails
     */
    public Score[] getHighestScores() throws SQLException {
        PreparedStatement statement = getStatement(getQuery);

        //statement.setString(1, object.getUsername());
        ResultSet rs = statement.executeQuery();
        Score[] result = new Score[10];
        int i = 0;
        while (rs.next()) {
            Score temp = new Score(rs.getInt("id"), rs.getString("username"), rs.getInt("value"));
            result[i] = temp;
            i++;
        }
        return result;
    }

    /**
     * Get the 10 highest scores of the player
     * @param object Player object
     * @return Score[] array of 10 Score objects
     * @throws SQLException when the statement fails
     */
    public Score[] getPlayerScores(Player object) throws SQLException {
        PreparedStatement statement = getStatement(getPlayerQuery);

        statement.setString(1, object.getUsername());
        ResultSet rs = statement.executeQuery();
        Score[] result = new Score[10];
        int i = 0;
        while (rs.next()) {
            Score temp = new Score(rs.getInt("id"), rs.getString("username"), rs.getInt("value"));
            result[i] = temp;
            i++;
        }
        return result;
    }

    /**
     * returns a Score based on id from the database
     * @param Score to be fetched must have unique identifier populated
     * @return Score object
     * @throws SQLException when the statement fails
     */
    @Override
    public Score get(Score object) throws SQLException {
        PreparedStatement statement = getStatement(getScoreQuery);

        statement.setInt(1, object.getId());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Score(rs.getInt(1), rs.getString(2), rs.getInt(3));
        } else {
            return null;
        }
    }
}
