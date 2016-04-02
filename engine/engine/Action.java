package engine;

public class Action {
	
	private int coordX;
	private int coordY;
	private Colors colorPlay;
	private boolean skip=false;
	private boolean kill=false;
	
	public Action(int coordX, int coordY, Colors colorPlay)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.colorPlay = colorPlay;
	}
	
	public Action()
	{
		skip=true;
	}
	
	public Action(int coordX, int coordY, boolean kill) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.kill = kill;
	}
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public Colors getColorPlay() {
		return colorPlay;
	}
	public void setColorPlay(Colors colorPlay) {
		this.colorPlay = colorPlay;
	}
	public boolean isSkip() {
		return skip;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	public boolean isKill() {
		return kill;
	}
	
	public String toString() {
		return "Action = [x="+coordX+", y="+coordY+", color="+colorPlay+", skip="+skip+", kill="+kill+"]";
	}
	
	
}
