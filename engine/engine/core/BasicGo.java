package engine.core;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import engine.Action;
import engine.Board;
import engine.Colors;
import engine.Panel;

public class BasicGo implements ICore {

	protected ICore nextCore;

	protected boolean small;
	protected Panel panel = null;
	protected boolean playSkip;
	protected boolean legalMove;
	protected Board board;
	protected boolean endGame;
	protected Colors colorPlaying;
	protected boolean computedScore;
	protected Action newAction = null;
	protected boolean ctrlPressed;
	protected boolean justSkip;
	protected Font font;
	protected Font fontB;
	protected TrueTypeFont ttf;
	protected TrueTypeFont ttfB;

	public BasicGo(boolean small) {
		if (small) {
			board = new Board(9, 76);
		} else {
			board = new Board(19, 36);
		}
		nextCore = this;
		this.small = small;
		colorPlaying = Colors.WHITE;
		legalMove = false;
		playSkip = false;
	}

	@Override
	public void init() throws SlickException {
		if (small) {
			panel = new Panel(board, new Image("images/background.png"));
			Colors.WHITE.setImg(new Image("images/white_token.png"));
			Colors.BLACK.setImg(new Image("images/black_token.png"));
		} else {
			panel = new Panel(board, new Image("images/background_19.png"));
			Colors.WHITE.setImg(new Image("images/white_token_19.png"));
			Colors.BLACK.setImg(new Image("images/black_token_19.png"));
		}
		font = new Font("Verdana", Font.BOLD, 10);
		fontB = new Font("Verdana", Font.BOLD, 20);
		ttf = new TrueTypeFont(font, true);
		ttfB = new TrueTypeFont(fontB, true);
		Colors.WHITE.setScore(0);
		Colors.BLACK.setScore(6.5);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		panel.paintComponent(gc, g);
		paint();

	}

	protected void paint() {
		ttf.drawString(15, 684 + 10, "CTRL + Z = Undo", Color.white);
		ttf.drawString(15, 684 + 20, "S = Skip", Color.white);
		ttfB.drawString(520, 680 + 10, "White = " + (Colors.WHITE.getScore()), Color.white);
		ttfB.drawString(520 + 6, 680 + 30, "Black = " + (Colors.BLACK.getScore()), Color.white);
		ttfB.drawString(200, 684 + 10, colorPlaying + "'s turn to play", Color.white);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {

		if (nextCore != this) {
			nextCore.init();
			ICore old_nextCore = nextCore;
			nextCore = this;
			return old_nextCore;
		}
		if (newAction != null) {
			/// choix du coup et verif de la l�galit�
			legalMove = board.isLegal(newAction);
			if (legalMove) {
				nextTurn(newAction);
			}
		}

		if (playSkip) {
			nextTurn(new Action());
		}
		return this;
	}

	protected void nextTurn(Action play) {
		if (play.isSkip()) {
			if (endGame && !computedScore) {
				board.groupForScore();
				computedScore = true;
			}
			if (justSkip) {
				endGame = true;
			}
			justSkip = true;
		} else {
			board.play(play);
			justSkip = false;
		}

		colorPlaying = colorPlaying.oppositeColor();
		legalMove = false;
		playSkip = false;
		System.out.println("BLACK :" + Colors.BLACK.getScore());
		System.out.println("WHITE :" + Colors.WHITE.getScore() + "\n");
		newAction = null;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (button == 0) {
			if (!computedScore) {
				if (endGame) {
					newAction = new Action(x / board.getPieceSize(), y / board.getPieceSize(), true);
				} else {
					newAction = new Action(x / board.getPieceSize(), y / board.getPieceSize(), colorPlaying);
				}
			}
		}

	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_S) {
			playSkip = true;
		}
		if (key == Input.KEY_Z && ctrlPressed) {
			board.replay(-1);
			colorPlaying = colorPlaying.oppositeColor();
		}

		if (key == Input.KEY_LCONTROL || key == Input.KEY_RCONTROL) {
			ctrlPressed = false;
		}

		if (key == Input.KEY_ESCAPE) {
			nextCore = new MenuInGame(this);
		}

	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_LCONTROL || key == Input.KEY_RCONTROL) {
			ctrlPressed = true;
		}
	}
}
