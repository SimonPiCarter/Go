package engine.core;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Menu implements ICore {

	private Image selection;
	private int itemSelected;
	
	private ICore nextCore;
	
	private Font font;
	private TrueTypeFont ttf;
	private boolean exit;
	
	@Override
	public void init() throws SlickException {
		selection = new Image("images/menu_select.png");
		itemSelected = 0;
		nextCore = this;
		font = new Font("Verdana", Font.BOLD, 20);
	    ttf = new TrueTypeFont(font, true);
	    exit = false;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ttf.drawString(220, 50, "Solo Player", Color.darkGray);
		ttf.drawString(190, 100, "Local Multi Player", Color.darkGray);
		ttf.drawString(170, 150, "Internet Multi Player", Color.darkGray);
		ttf.drawString(260, 200, "Exit", Color.darkGray);
		
		g.drawImage(selection, 0, 40+ itemSelected*50);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
		if ( exit ) {
			arg0.exit();
		}
		if ( nextCore != this ) {
			nextCore.init();
		}
		return nextCore;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char c) {
		if ( key == Input.KEY_UP ) {
			itemSelected = Math.max(0, itemSelected-1);
		}
		if ( key == Input.KEY_DOWN ) {
			itemSelected = Math.min(3, itemSelected+1);
		}
		if ( key == Input.KEY_ENTER || key == Input.KEY_SPACE ) {
			switch (itemSelected ) {
			case 0:
				break;
			case 1:
				nextCore = new BasicGo(false);
				break;
			case 2:
				//nextCore = new MultiplayerMenu();
				break;
			case 3:
				exit = true;
				break;
			default:
				break;
			}
		}
	}

}
