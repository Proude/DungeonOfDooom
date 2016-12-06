package com.dod.db.repositories;

import com.dod.models.Score;

import java.sql.SQLException;

/**
 * An interface to make testing components that depend on ScoreRepository easier
 */
public interface IScoreRepository {
    Score get(Score object) throws SQLException;
    boolean delete(Score object) throws SQLException;
    boolean insert(Score object) throws SQLException;
}
