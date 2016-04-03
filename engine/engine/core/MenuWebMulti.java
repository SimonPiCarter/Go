package engine.core;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import engine.Colors;
import server.ClientPlayer;
import server.ServerPlayer;

public class MenuWebMulti extends Menu {
	
	protected ICore lastCore;
	
	public MenuWebMulti(ICore lastCore) {
		this.lastCore = lastCore;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ttf.drawString(260+16, 50, "Host Game", Color.darkGray);
		ttf.drawString(260+16, 100, "Join game", Color.darkGray);
		ttf.drawString(300+16, 150, "Back", Color.darkGray);
		
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
				try {
					nextCore = new OnlineGo(true,new ServerPlayer(),Colors.WHITE);
				} catch (IOException e) {
					System.out.println("Failed to host game");
				}
				break;
			case 1:
				try {
					nextCore = new OnlineGo(true,new ClientPlayer("localhost"),Colors.BLACK);
				} catch (IOException e) {
					System.out.println("Failed to host game");
				}
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
