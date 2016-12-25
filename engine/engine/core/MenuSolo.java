package engine.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import engine.Colors;
import server.AIPlayer;

public class MenuSolo extends Menu {

	protected ICore lastCore;

	private boolean small = true;
	private boolean white = true;

	public MenuSolo(ICore lastCore) {
		this.lastCore = lastCore;
	}

	@Override
	public void init() throws SlickException {
		super.init();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (small) {
			ttf.drawString(250 + 16, 50, "Small board (9x9)", Color.darkGray);
		} else {
			ttf.drawString(220 + 16, 50, "Normal Board (19x19)", Color.darkGray);
		}
		if (white) {
			ttf.drawString(295 + 16, 100, "White", Color.darkGray);
		} else {
			ttf.drawString(295 + 16, 100, "Black", Color.darkGray);
		}

		ttf.drawString(290 + 16, 150, "Start", Color.darkGray);

		ttf.drawString(300 + 16, 200, "Back", Color.darkGray);

		g.drawImage(selection, 0, 40 + itemSelected * 50);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
		if (nextCore != this && nextCore != lastCore) {
			nextCore.init();
		}
		if (nextCore != this) {
			nextCore.init();
		}
		ICore old_nextCore = nextCore;
		nextCore = this;
		return old_nextCore;
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_UP) {
			itemSelected = Math.max(0, itemSelected - 1);
		}
		if (key == Input.KEY_DOWN) {
			itemSelected = Math.min(3, itemSelected + 1);
		}

		if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			switch (itemSelected) {
			case 0:
				small = !small;
				break;
			case 1:
				white = !white;
				break;
			case 2:
				AIPlayer player = new AIPlayer(white ? Colors.BLACK : Colors.WHITE);
				OnlineGo go = new OnlineGo(small, player, white ? Colors.WHITE : Colors.BLACK);
				player.setBoard(go.board);
				nextCore = go;
				break;
			case 3:
				nextCore = lastCore;
				break;
			default:
				break;
			}
		}
	}

}
