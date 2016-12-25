package engine;

import org.newdawn.slick.Image;

public enum Colors {
	BLACK, WHITE, BORDER, EMPTY;
	double score = 0;
	Image img;

	public void scoreUp(double offset) {
		score += offset;
	}

	public Colors oppositeColor() {

		if (this == Colors.BLACK)
			return Colors.WHITE;
		if (this == Colors.WHITE)
			return Colors.BLACK;
		return Colors.EMPTY;
	}

	public boolean isEnemy(Colors other) {
		return other.equals(this.oppositeColor()) || other.equals(BORDER);
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}
