package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;

/**
 * Calculates the visible tiles from the perspective of a particular Character
 */
public interface IVisibilityService {

    /**
     * Generates a copy of a Map with the correct isVisible flags set for the perspective of a particular Character
     * @param deungeonMap the Map  pchar resides in
     * @param pchar the Character the perspective of which we're generating visibility with
     * @return a copy of dungeonMap with correct isVisible flags set for the perspective of pchar
     */
    Map createVisibleMap(Map deungeonMap, Character pchar);

}
