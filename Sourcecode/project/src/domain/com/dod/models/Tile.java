package DungeonOfDoom;

import java.io.Serializable;

public class Tile implements Serializable{

	private static final long serialVersionUID = 1L;
	protected int type;
	protected boolean visibility;
	
	public Tile(int type, boolean visibility){
		this.setType(type);
		this.setVisibility(visibility);
	}
	
	public Tile() {
	
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean getVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public String toString(){
		return "Type: "+this.type+"\nVisibility: "+this.visibility;	
	}
	
}

