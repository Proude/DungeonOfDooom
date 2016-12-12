package com.dod.service.service;

import com.dod.models.Character;
import com.dod.models.Map;
import com.dod.models.Player;
import com.dod.models.Point;

/**
 * Interface for MovementService.
 * Handles game logic to move a character from one point to another.
 */
public interface IMovementService {

    Point Move(String direction, Player player); //todo add required params- eg map, character, target point

}
