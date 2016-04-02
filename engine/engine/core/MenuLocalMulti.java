package engine.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MenuLocalMulti extends Menu {
	
	protected ICore lastCore;
	
	public MenuLocalMulti(ICore lastCore) {
		this.lastCore = lastCore;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ttf.drawString(220+16, 50, "Small Board (9x9)", Color.darkGray);
		ttf.drawString(190+16, 100, "Normal Board (19x19)", Color.darkGray);
		ttf.drawString(260+16, 150, "Back", Color.darkGray);
		
		g.drawImage(selection, 0, 40+ itemSelected*50);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
		if ( nextCore != this && nextCore != lastCore ) {
			nextCore.init();
		}
		return nextCore;
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( key == Input.KEY_UP ) {
			itemSelected = Math.max(0, itemSelected-1);
		}
		if ( key == Input.KEY_DOWN ) {
			itemSelected = Math.min(2, itemSelected+1);
		}
		if ( key == Input.KEY_ENTER || key == Input.KEY_SPACE ) {
			switch (itemSelected ) {
			case 0:
				nextCore = new BasicGo(true);
				break;
			case 1:
				nextCore = new BasicGo(false);
				break;
			case 2:
				nextCore = lastCore;
				break;
			default:
				break;
			}
		}
	}

}
