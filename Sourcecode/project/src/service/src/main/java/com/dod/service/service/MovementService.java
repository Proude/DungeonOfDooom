package com.dod.service.service;

import com.dod.game.IMatchList;
import com.dod.game.MatchList;
import com.dod.models.*;
import com.dod.models.Character;

/**
 * Implementation of IMovementService
 */
public class MovementService implements IMovementService {

    IMatchList matchList;

    public MovementService() { this.matchList = MatchList.instance(); }

    @Override
    public Point Move(String direction, Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Character pChar = match.getCharacter(player.getUsername());
        Map dungeonMap = match.getMap();
        Point newPoint;

        switch (direction) {
            case "W":
                // check if movement valid
                newPoint = new Point(pChar.getPosition().x, pChar.getPosition().y - 1);
                return updatePosition(newPoint, dungeonMap, pChar);
            case "D":
                newPoint = new Point(pChar.getPosition().x + 1, pChar.getPosition().y);
                return updatePosition(newPoint, dungeonMap, pChar);
            case "S":
                newPoint = new Point(pChar.getPosition().x, pChar.getPosition().y + 1);
                return updatePosition(newPoint, dungeonMap, pChar);
            case "A":
                newPoint = new Point(pChar.getPosition().x - 1, pChar.getPosition().y);
                return updatePosition(newPoint, dungeonMap, pChar);
            default:
                return pChar.getPosition();
        }
    }

    private Point updatePosition(Point newPoint, Map dungeonMap, Character pChar) {
        if (dungeonMap.getTile(newPoint).getType() == 1) {
            pChar.setPosition(newPoint);
        } else if (dungeonMap.getTile(newPoint).getType() == 2){
            pChar.setPosition(newPoint);
            pChar.setCollectedCoins(pChar.getCollectedCoins() + 1);
            dungeonMap.setTile(newPoint, new Tile(1, true));
        }
        return pChar.getPosition();
    }
}
