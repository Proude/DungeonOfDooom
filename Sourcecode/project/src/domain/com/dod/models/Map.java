package DungeonOfDoom;

import java.io.Serializable;

public class Map implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String name;
	protected int coin_no;
	protected int coin_win;
	protected Tile[][] tile;
	
	
	public Map() {
	
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
}
	
	