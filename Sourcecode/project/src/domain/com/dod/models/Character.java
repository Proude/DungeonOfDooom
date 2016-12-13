package com.dod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The model of character
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public List<Point> getCollectedCoinsPos() {
        return collectedCoinsPos;
    }

    public void setCollectedCoinsPos(Point newPoint) {
        this.collectedCoinsPos.add(newPoint);
    }
}
