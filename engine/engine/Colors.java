package engine;

import java.awt.Image;

public enum Colors {
	BLACK,
	WHITE,
	BORDER,
	EMPTY;
	int score=0;
	Board boardKo;
	Image img;
	public Board getBoardKo(){return boardKo;}
	public void setBoardKo(Board boar){this.boardKo=boar;}
	public void scoreUp(int offset){score=+offset;}
	public Colors oppositeColor(){
		
		if(this==Colors.BLACK)return Colors.WHITE;
		if(this==Colors.WHITE)return Colors.BLACK;
		return Colors.EMPTY;
	}
	
}
