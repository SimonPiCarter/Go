package engine.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import engine.Colors;
import server.ServerPlayer;

public class MenuWebMultiHost extends Menu {
	
	protected ICore lastCore;
	
	private ServerPlayer player;
	
	private boolean small = true;
	private boolean white = true;
	
	public MenuWebMultiHost(ICore lastCore) {
		this.lastCore = lastCore;
	}
	
	@Override
	public void init() throws SlickException {
		super.init();
		player = new ServerPlayer();
	}

	private long elapsedTime = System.currentTimeMillis();
	private String[] waiting = {
			"Waiting for player to connect",
			"Waiting for player to connect.",
			"Waiting for player to connect..",
			"Waiting for player to connect..."
			};
	private int waitingIndex = 0;
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if ( small ) {
			ttf.drawString(250+16, 50, "Small board (9x9)", Color.darkGray);
		} else {
			ttf.drawString(220+16, 50, "Normal Board (19x19)", Color.darkGray);
		}
		if ( white ) {
			ttf.drawString(295+16, 100, "White", Color.darkGray);
		} else {
			ttf.drawString(295+16, 100, "Black", Color.darkGray);
		}
		
		if ( !player.isConnected() ) {
			ttf.drawString(250+16, 150, "Create Game", Color.darkGray);
		} else {
			ttf.drawString(290+16, 150, "Start", Color.darkGray);
		}
		
		ttf.drawString(300+16, 200, "Back", Color.darkGray);
		
		if ( player.isAlive() && !player.isConnected() ) {
			long currentTime = System.currentTimeMillis();
			if ( currentTime-elapsedTime > 1000 ) {
				waitingIndex++;
				if ( waitingIndex >= waiting.length )  {
					waitingIndex = 0;
				}
				elapsedTime = currentTime;
			}
			ttf.drawString(0, 300, waiting[waitingIndex], Color.darkGray);
		}
		
		g.drawImage(selection, 0, 40+ itemSelected*50);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
		if ( nextCore != this && nextCore != lastCore ) {
			nextCore.init();
		}
		if ( nextCore != this ) {
			nextCore.init();
		}
		ICore old_nextCore = nextCore;
		nextCore = this;
		return old_nextCore;
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( key == Input.KEY_UP ) {
			itemSelected = Math.max(0, itemSelected-1);
		}
		if ( key == Input.KEY_DOWN ) {
			itemSelected = Math.min(3, itemSelected+1);
		}
		
		if ( key == Input.KEY_ENTER || key == Input.KEY_SPACE ) {
			switch (itemSelected ) {
			case 0:
				small = !small;
				break;
			case 1:
				white = !white;
				break;
			case 2:
				if ( !player.isAlive() ) {
					player.start();
				} else if ( player.isConnected() ) {
					nextCore  = new OnlineGo(small, player, white? Colors.WHITE : Colors.BLACK);
				}
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
