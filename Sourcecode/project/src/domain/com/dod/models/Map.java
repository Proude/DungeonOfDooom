package com.dod.models;

import java.io.Serializable;
import java.util.Random;

/**
 * <pre>
        A Map stores a 2-dimensional grid of Tiles.
        A Map has a name, width, height and numbe rof coins total and required to win.
 * </pre>
 */
public class Map {

	protected int width;
	protected int height;
	protected String name;
	protected int totalNumberOfCoins;
	protected int numberOfCoinsNeededToWin;
	protected Tile[][] tiles;

	public Map(int width, int height) {
		tiles = new Tile[width][height];
	}

	public Map(String name, int totalNumberOfCoins, int numberOfCoinsNeededToWin, int width, int height, Point mapSize) {
		this.name = name;
		this.totalNumberOfCoins = totalNumberOfCoins;
		this.numberOfCoinsNeededToWin = numberOfCoinsNeededToWin;
		this.width = width;
		this.height = height;
		tiles = new Tile[mapSize.x][mapSize.y];
	}

	public void setTile(Point position, Tile tile) {
		tiles[position.x][position.y] = tile;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

    /**
     * The total number of coins that should be created in the map.
     * @return int
     */
	public int getCoinNo(){
		return totalNumberOfCoins;
	}

    /**
     * The total number of coins that should be created in the map.
     * @param coin_no int
     */
	public void setCoinNo(int coin_no){
		this.totalNumberOfCoins = coin_no;
	}

    /**
     * The total number of coins needed to win on this map
     * @return int
     */
	public int getCoinWin(){
		return numberOfCoinsNeededToWin;
	}

    /**
     * The total number of coins needed to win on this map
     * @param coin_win int
     */
	public void setCoinWin(int coin_win){
		this.numberOfCoinsNeededToWin = coin_win;
	}

	public Tile getTile(Point point) {
		return tiles[point.x][point.y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

    /**
     * Gets a random position of a tile that is not a wall, coin or exit.
     * @return Point
     */
	public Point getRandomFreeTilePoint() {
		Random random = new Random();
		Point point = null;

		while(point == null) {
			int x = random.nextInt(width-1);
			int y = random.nextInt(height-1);

			if(tiles[x][y].getType() == TileType.Empty.getValue()) {
				point = new Point(x,y);
			}
		}

		return point;
	}
}
	
	