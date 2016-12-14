package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.GameStateModel;

/**
 * Generates a representation of the current game state form the perspective of a particular character
 */
public interface IStateService {

    /**
     * Generates and returns a representation of the current game state form the perspective of a particular character
     * @param player Player the Player a GameStateModel will be generated for
     * @return GameStateModel a model of the current game state
     */
    GameStateModel GetState(Player player);

}
