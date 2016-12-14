package com.dod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     A Character is a fictional entity that moves around the game world.
 *     A Character belongs to a Player.
 *     A Character has a position and can interact with coins and the exit.
 * </pre>
 */
public class Character {

    private Point position;
    private Player player;
    private int collectedCoins;
    private List<Point> collectedCoinsPos;

    public Character(Point position, Player player) {
        this.position = position;
        this.player = player;
        this.collectedCoinsPos = new ArrayList<>();
        collectedCoins = 0;
    }

    /**
     * The player's position in the game world
     * @return Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * The player's position in the game world
     * @param position Point
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * The Player that this Character belongs to
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The Player that this Character belongs to
     * @param player Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    /**
     * Keeps track of which coins on the map this Character has collected.
     * This enables us to leave the coin on the Map once it has been picked up, thereby allowing other players
     * to pick it up, and yet not send the same coin to the same player's client again.
     * @return a list of Point objects that represent the points on the map where the Character has collected a coin
     */
    public List<Point> getCollectedCoinsPos() {
        return collectedCoinsPos;
    }

    /**
     * Keeps track of which coins on the map this Character has collected.
     * This enables us to leave the coin on the Map once it has been picked up, thereby allowing other players
     * to pick it up, and yet not send the same coin to the same player's client again.
     * @param newPoint the Point to add to the collection
     */
    public void addCollectedCoinsPos(Point newPoint) {
        this.collectedCoinsPos.add(newPoint);
    }
}
