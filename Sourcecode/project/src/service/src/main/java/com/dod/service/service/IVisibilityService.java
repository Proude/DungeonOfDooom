package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;

/**
 * Interface for VisibilityService
 * Calculates the visible tiles from a particular point for a particular distance
 */
public interface IVisibilityService {

    Map createVisibleMap(Map deungeonMap, Character pchar);
    Map getVisibleTilesForCharacter(Map visibleMap, Map dungeonMap, Character pchar); //todo return 2d tiles obj array and add char param

}
