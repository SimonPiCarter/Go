package engine.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MenuInGame extends Menu {

	protected ICore lastCore;

	public MenuInGame(ICore lastCore) {
		this.lastCore = lastCore;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ttf.drawString(260 + 16, 50, "Resume Game", Color.darkGray);
		ttf.drawString(235 + 16, 100, "Back to Main Menu", Color.darkGray);
		ttf.drawString(280 + 16, 150, "Exit Game", Color.darkGray);

		g.drawImage(selection, 0, 40 + itemSelected * 50);
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_UP) {
			itemSelected = Math.max(0, itemSelected - 1);
		}
		if (key == Input.KEY_DOWN) {
			itemSelected = Math.min(2, itemSelected + 1);
		}
		if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			switch (itemSelected) {
			case 0:
				nextCore = lastCore;
				break;
			case 1:
				nextCore = new Menu();
				break;
			case 2:
				exit = true;
				break;
			default:
				break;
			}
		}
	}

}
