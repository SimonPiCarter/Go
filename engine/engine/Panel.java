package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Panel {
	Board board;
	private Image background = null;

	public Panel(Board board, Image background) throws SlickException {
		this.board = board;
		this.background = background;
	}

	public void paintComponent(GameContainer gc, Graphics g) {

		g.drawImage(background, 0, 0);

		for (int x = 0; x < board.getSize(); ++x) {
			for (int y = 0; y < board.getSize(); ++y) {
				if (board.getCell(x, y).getCellColor() != Colors.EMPTY) {
					this.drawStone(gc, g, board.getCell(x, y));
				}
			}
		}
	}

	private void drawStone(GameContainer gc, Graphics g, Cell cell) {
		g.drawImage(cell.getCellColor().img, cell.getCoordX() * board.getPieceSize(),
				cell.getCoordY() * board.getPieceSize());
	}

}
