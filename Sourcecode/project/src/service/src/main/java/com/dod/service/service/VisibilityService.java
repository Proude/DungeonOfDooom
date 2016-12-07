package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;
import com.dod.models.Point;

/**
 * Implements IVisibilityService
 */
public class VisibilityService implements IVisibilityService {

    @Override
    public Map getVisibleTilesForCharacter(Map dungeonMap, Character pchar) {

        Map returnValue = new Map(dungeonMap.getWidth(), dungeonMap.getHeight());
        for (int i = 0; i < dungeonMap.getWidth(); i++) {
            for (int j = 0; j < dungeonMap.getHeight(); j++) {
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
