package com.dod.db.repositories;

import com.dod.models.Player;
import com.dod.models.Score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  ScoreRepository is used to establish connection with database
 *  and store, delete or get items from it.
 */
public class ScoreRepository extends DatabaseRepository<Score> {

    private final String getPlayerQuery = "SELECT * FROM score WHERE username='?' ORDER BY value DESC LIMIT 10";
    private final String deleteQuery = "DELETE FROM score WHERE id = ?";
    private final String getScoreQuery = "SELECT * FROM score WHERE id = ?";
    private final String getQuery = "SELECT * FROM score ORDER BY value DESC LIMIT 10";
    private final String insertQuery = "INSERT INTO score (username, value) VALUES (?, ?)";

    /**
     * Inserts a score value to score table of database based on player's
     * username.
     * @param playerObject current player
     * @param scoreObject current score that we need to score
     * @return true if insertion was successful else false
     * @throws SQLException
     */
    public boolean insert(Player playerObject, Score scoreObject) throws SQLException {

        PreparedStatement statement = getStatement(insertQuery);

        statement.setString(1, playerObject.getUsername());
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
     * @throws SQLException
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
     * return 10 highest scores from database so they
     * can be printed on ScoreView
     * @return maximum 10 scores
     * @throws SQLException
     */
    public Score[] getHighestScore() throws SQLException {
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
     * returns 10 highest scores of the player from database
     * so they can be printed on ScoreView
     * @param object Player object
     * @return maximum 10 scores
     * @throws SQLException
     */
    public Score[] getPlayerScore (Player object) throws SQLException {
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
     * returns a score based on id from the database
     * @param object score object
     * @return score object
     * @throws SQLException
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
