package Blockade.Players;
import java.util.*;


abstract public class Player{
	private final int[] origCoord;
	private int[] curCoord;
	public Player(int curX, int curY){
		this.origCoord = new int[]{curX, curY};
		this.curCoord = new int[]{curX, curY};
	}

	public int getOriginalX(){
		return this.origCoord[0];
	}

	public int getOriginalY(){
		return this.origCoord[1];
	}

	public int[] getOringalCoordinates(){
		return this.origCoord;
	}

	public int getCurrentX(){
		return this.curCoord[0];
	}

	public int getCurrentY(){
		return this.curCoord[1];
	}

	public int[] getCurruntCoordinates(){
		return this.curCoord;
	}

	public void setCurrentX(int x){
		this.curCoord[0] = x;
	}

	public void setCurrentY(int y){
		this.curCoord[1] = y;
	}

	public void setCurrentCoordinates(int[] coords){
		this.curCoord = coords.clone();
	}

}
