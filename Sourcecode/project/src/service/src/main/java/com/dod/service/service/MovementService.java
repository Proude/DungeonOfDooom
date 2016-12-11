package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Character;

/**
 * Implementation of IMovementService
 */
public class MovementService implements IMovementService {

    @Override
    public boolean Move(String direction, Character pChar, Map dungeonMap) {
        return true;
    }

}
