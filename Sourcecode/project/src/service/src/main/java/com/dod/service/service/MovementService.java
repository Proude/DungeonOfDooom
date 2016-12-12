package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Character;
import com.dod.models.Point;
import com.dod.models.Tile;

/**
 * Implementation of IMovementService
 */
public class MovementService implements IMovementService {

    @Override
    public Point Move(String direction, Character pChar, Map dungeonMap) {
        Point newPoint;
        switch (direction) {
            case "W":
                // check if movement valid
                newPoint = new Point(pChar.getPosition().x, pChar.getPosition().y - 1);
                if (dungeonMap.getTile(newPoint).getType() == 1) {
                    pChar.setPosition(newPoint);
                    return newPoint;
                } else {
                    return pChar.getPosition();
                }
            case "D":
                newPoint = new Point(pChar.getPosition().x + 1, pChar.getPosition().y);
                if (dungeonMap.getTile(newPoint).getType() == 1) {
                    pChar.setPosition(newPoint);
                    return newPoint;
                } else {
                    return pChar.getPosition();
                }
            case "S":
                newPoint = new Point(pChar.getPosition().x, pChar.getPosition().y + 1);
                if (dungeonMap.getTile(newPoint).getType() == 1) {
                    pChar.setPosition(newPoint);
                    return newPoint;
                } else {
                    return pChar.getPosition();
                }
            case "A":
                newPoint = new Point(pChar.getPosition().x - 1, pChar.getPosition().y);
                if (dungeonMap.getTile(newPoint).getType() == 1) {
                    pChar.setPosition(newPoint);
                    return newPoint;
                } else {
                    return pChar.getPosition();
                }
            default:
                return pChar.getPosition();
        }
    }

}
