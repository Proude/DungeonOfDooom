package com.dod.service.service;

import com.dod.db.repositories.IScoreRepository;
import com.dod.db.repositories.ScoreRepository;
import com.dod.game.IMatchList;
import com.dod.game.MatchList;
import com.dod.models.*;
import com.dod.models.Character;

import java.sql.SQLException;
import java.util.Date;

/**
 * Implementation of IMovementService
 */
public class MovementService implements IMovementService {

    IMatchList matchList;
    IScoreRepository scoreRepository;

    public MovementService() {
        this.matchList = MatchList.instance();
        this.scoreRepository = (IScoreRepository)new ScoreRepository();
    }

    @Override
    public Point Move(String direction, Player player) throws SQLException {
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

    private Point updatePosition(Point newPoint, Map dungeonMap, Character pChar) throws SQLException {
        if (dungeonMap.getTile(newPoint).getType() == TileType.Empty.getValue()) {
            pChar.setPosition(newPoint);
        } else if (dungeonMap.getTile(newPoint).getType() == TileType.Coin.getValue()){
            pChar.setPosition(newPoint);
            if (!pChar.getCollectedCoinsPos().contains(newPoint)) {
                pChar.setCollectedCoins(pChar.getCollectedCoins() + 1);
                pChar.setCollectedCoinsPos(newPoint);
            }
        }
        else if(dungeonMap.getTile(newPoint).getType() == TileType.Exit.getValue()) {
            if(pChar.getCollectedCoins() > dungeonMap.getCoinWin()) {
                pChar.setPosition(newPoint);
                matchList.getMatchForPlayer(pChar.getPlayer().getUsername()).setState(MatchState.Over);
                Match match = matchList.getMatchForPlayer(pChar.getPlayer().getUsername());
                Date temp = new Date();
                match.setTimer(temp.getTime() - match.getTimer());
                scoreRepository.insert(new Score(pChar.getPlayer().getUsername(), (int) (((double)pChar.getCollectedCoins() / match.getTimer()) * 10000000)));
            }
        }
        return pChar.getPosition();
    }
}
