package com.dod.models;

import java.io.Serializable;

/**
 * <pre>
 *     A Tile represents single tile on the grid that is the Map
 *     A Tile has a Type that indicates whether it is eg a wall, floor, coin or exit tile.
 *     A Tile may or may not be visible
 * </pre>
 */
public class Tile {

	protected int type;
	protected boolean visibility;
	
	public Tile(int type, boolean visibility){
		this.setType(type);
		this.setVisibility(visibility);
	}

	public Tile(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isVisible() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public String toString(){
		return "Type: "+this.type+"\nVisibility: "+this.visibility;	
	}
	
}

