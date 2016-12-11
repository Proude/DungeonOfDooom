package com.dod.service.service;

import com.dod.models.Character;
import com.dod.models.Map;

/**
 * Interface for MovementService.
 * Handles game logic to move a character from one point to another.
 */
public interface IMovementService {

    boolean Move(String direction, Character pChar, Map dungeonMap); //todo add required params- eg map, character, target point

}
