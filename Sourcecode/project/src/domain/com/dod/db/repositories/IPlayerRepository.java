package com.dod.db.repositories;

import com.dod.models.Player;

import java.sql.SQLException;

/**
 * Created by Fortnox on 06/12/2016.
 */
public interface IPlayerRepository {

    Player get(Player object) throws SQLException;
    boolean delete(Player object) throws SQLException;
    boolean insert(Player object) throws SQLException;
}
