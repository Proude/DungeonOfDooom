package com.dod.service.service;

import com.dod.models.Character;
import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Point;

import java.sql.SQLException;

/**
 * Interface for MovementService.
 * Handles game logic to move a character from one point to another.
 */
public interface IMovementService {

    /**
     * Moves the Player in a particular direction. Will increment player's gold if interacting with gold coins, can
     * trigger end of the Match when player interacts with Exit.
     * @param direction String a char from {W,S,A,D} pertaining to a particular direction in the WASD layout
     * @param player Player whom's Character will be moved
     * @return Point that the Player has moved to
     * @throws SQLException if the database cannot be reached or statement fails while inserting new score
     */
    Point Move(String direction, Player player) throws SQLException;

}
