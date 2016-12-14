package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;
import com.dod.models.Point;
import com.dod.models.Tile;

/**
 * Calculates the visible tiles from the perspective of a particular Character
 */
public class VisibilityService implements IVisibilityService {

    /**
     * Generates a copy of a Map with the correct isVisible flags set for the perspective of a particular Character
     * @param deungeonMap the Map  pchar resides in
     * @param pchar the Character the perspective of which we're generating visibility with
     * @return a copy of dungeonMap with correct isVisible flags set for the perspective of pchar
     */
    @Override
    public Map createVisibleMap(Map dungeonMap, Character pchar) {
        Map returnValue = new Map(dungeonMap.getName(), dungeonMap.getCoinNo(), dungeonMap.getCoinWin(), dungeonMap.getWidth(), dungeonMap.getHeight(), new Point(dungeonMap.getWidth(), dungeonMap.getHeight()));
        for (int i = 0; i < dungeonMap.getWidth(); i++) {
            for (int j = 0; j < dungeonMap.getHeight(); j++) {
                if (pchar.getCollectedCoinsPos().contains(new Point(i, j)))
                    returnValue.setTile(new Point(i, j), new Tile(1, true));
                else
                    returnValue.setTile(new Point(i,j), dungeonMap.getTile(new Point(i, j)));
                if (pchar.getPosition().x - 2 > i || pchar.getPosition().x + 2 < i||pchar.getPosition().y -2 > j||pchar.getPosition().y+2 < j)
                    returnValue.getTile(new Point(i, j)).setVisibility(false);
                else
                    returnValue.getTile(new Point(i, j)).setVisibility(true);
            }
        }
        return returnValue;
    }
}
