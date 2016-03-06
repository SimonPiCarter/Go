package engine;

import org.newdawn.slick.Image;

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
	public void scoreUp(int offset){score+=offset;}
	public Colors oppositeColor(){
		
		if(this==Colors.BLACK)return Colors.WHITE;
		if(this==Colors.WHITE)return Colors.BLACK;
		return Colors.EMPTY;
	}
	
	public boolean isEnemy(Colors other) {
		return other.equals(this.oppositeColor()) || other.equals(BORDER);
	} 
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	
}
