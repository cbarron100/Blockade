package Players;

public class Playable extends Player{
	private String colour;
	public Playable(int x, int y, String colour){
		super(x, y);

		this.colour = colour;
	}

	public String getColour(){
		return this.colour;
	}
}
