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

    /**
     * Moves the Player in a particular direction. Will increment player's gold if interacting with gold coins, can
     * trigger end of the Match when player interacts with Exit.
     * @param direction String a char from {W,S,A,D} pertaining to a particular direction in the WASD layout
     * @param player Player whom's Character will be moved
     * @return Point that the Player has moved to
     * @throws SQLException if the database cannot be reached or statement fails while inserting new score
     */
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

    /**
     * Decides whether or not to update the Player's Position and interacts with special Tiles.
     * @param newPoint Point the Point the Character wishes to move to
     * @param dungeonMap Map that the Character is moving in
     * @param pChar Character that is moving
     * @return Point the Point that the Character is now in
     * @throws SQLException if the database cannot be reached or statement fails while inserting new score
     */
    private Point updatePosition(Point newPoint, Map dungeonMap, Character pChar) throws SQLException {
        if (dungeonMap.getTile(newPoint).getType() == TileType.Empty.getValue()) {
            pChar.setPosition(newPoint);
        } else if (dungeonMap.getTile(newPoint).getType() == TileType.Coin.getValue()){
            pChar.setPosition(newPoint);
            if (!pChar.getCollectedCoinsPos().contains(newPoint)) {
                pChar.setCollectedCoins(pChar.getCollectedCoins() + 1);
                pChar.addCollectedCoinsPos(newPoint);
            }
        }
        else if(dungeonMap.getTile(newPoint).getType() == TileType.Exit.getValue()) {
            if(pChar.getCollectedCoins() > dungeonMap.getCoinWin()) {
                pChar.setPosition(newPoint);
                Match match = matchList.getMatchForPlayer(pChar.getPlayer().getUsername());
                match.setState(MatchState.Over);

                Date date = new Date();
                match.setTimer(date.getTime() - match.getTimer());
                int score = ((int) ((double)pChar.getCollectedCoins() / (double)match.getTimer() * 10000000));

                match.setScore(score);
                scoreRepository.insert(new Score(pChar.getPlayer().getUsername(), score));
            }
        }
        return pChar.getPosition();
    }
}
