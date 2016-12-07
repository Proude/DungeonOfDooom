package com.dod.models;

import java.io.Serializable;
import java.util.Random;

public class Map implements Serializable{

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected String name;
	protected int coin_no;
	protected int coin_win;
	protected Tile[][] tiles;

	public Map(int width, int height) {
		tiles = new Tile[width][height];
	}

	public Map(String name, int coin_no, int coin_win, int width, int height, Point mapSize) {
		this.name = name;
		this.coin_no = coin_no;
		this.coin_win = coin_win;
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

	public int getCoinNo(){
		return coin_no;
	}

	public void setCoinNo(int coin_no){
		this.coin_no = coin_no;
	}

	public int getCoinWin(){
		return coin_win;
	}

	public void setCoinWin(int coin_win){
		this.coin_win = coin_win;
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

	public Point getRandomFreeTilePoint() {
		Random random = new Random();
		Point point = null;

		while(point == null) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);

			if(tiles[x][y].getType() == TileType.Empty.getValue()) {
				point = new Point(x,y);
			}
		}

		return null;
	}
}
	
	