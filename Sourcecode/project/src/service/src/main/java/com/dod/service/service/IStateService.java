package com.dod.service.service;

import com.dod.models.Player;
import com.dod.service.model.GameStateModel;

/**
 * Interface for State Service
 * Generates a representation of the current game state form the perspective of
 * a particular character
 */
public interface IStateService {

    GameStateModel GetState(Player player);

}
