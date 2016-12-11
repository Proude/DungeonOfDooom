package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;
import com.dod.models.Point;

/**
 * Implements IVisibilityService
 */
public class VisibilityService implements IVisibilityService {

    @Override
    public Map createVisibleMap(Map dungeonMap, Character pchar) {
        Map returnValue = new Map(dungeonMap.getName(), dungeonMap.getCoinNo(), dungeonMap.getCoinWin(), dungeonMap.getWidth(), dungeonMap.getHeight(), new Point(dungeonMap.getWidth(), dungeonMap.getHeight()));
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

    @Override
    public Map getVisibleTilesForCharacter(Map returnValue, Map dungeonMap, Character pchar) {

        for (int i = 0; i < dungeonMap.getWidth(); i++) {
            for (int j = 0; j < dungeonMap.getHeight(); j++) {
                if (!(pchar.getPosition().x - 2 > i || pchar.getPosition().x + 2 < i||pchar.getPosition().y -2 > j||pchar.getPosition().y+2 < j))
                    returnValue.getTile(new Point(i, j)).setVisibility(true);
            }
        }
        return returnValue;
    }

}
